package com.github.surpassm.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.surpassm.common.tool.util.DateUtil;
import com.github.surpassm.security.exception.SurpassmAuthenticationException;
import com.github.surpassm.security.properties.SecurityProperties;
import com.github.surpassm.security.util.JwtTokenUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义登陆成功配置
 * 继承AuthenticationSuccessHandler
 * 默认成功处理器：SavedRequestAwareAuthenticationSuccessHandler
 *
 * @author mc
 * date 2018/08/27 7:11
 */
@Component("surpassmAuthenticationSuccessHandler")
public class SurpassmAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private ClientDetailsService clientDetailsService;
    @Resource
    private TokenStore tokenStore;
    @Resource
    private AuthorizationServerTokenServices myDefaultTokenServices;
    @Resource
    private SecurityProperties securityProperties;

    /**
     * 该方法在登陆成功以后会被调用
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        logger.info("登录成功");
        String header = request.getHeader(securityProperties.getHeaderLogin());
        String basic = securityProperties.getHeaderLoginValue();
        if (header == null || !header.startsWith(basic)) {
            throw new SurpassmAuthenticationException("请求头中无client信息");
        }
        String[] tokens = extractAndDecodeHeader(header, request);
        assert tokens.length == 2;
        //获取用户名
        String clientId = tokens[0];
        //获取密码
        String clientSecret = tokens[1];
        //根据用户名得到clientDetails
        ClientDetails clientDetails = null;
        try {
            clientDetails = clientDetailsService.loadClientByClientId(clientId);
        } catch (Exception e) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配" + clientId);
        }
        //验证clientDetails
        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("client配置不存在" + clientId);
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配" + clientId);
        }
        if (StringUtils.isEmpty(securityProperties.getOAuth2().getStoreType())) {
            throw new UnapprovedClientAuthenticationException("请配置TOKEN存储方式->oAuth2-storeType{redis or jwt}");
        }
        //返回前端JSON
        response.setContentType("application/json;charset=UTF-8");
        //获取登陆成功的当前用户信息
        Object principal = authentication.getPrincipal();
        // 创建一个TokenRequest,空MAP、clientId、Scope具备权限、custom自定义grantType标识
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_SORTED_MAP, clientId, clientDetails.getScope(), "custom");
        //创建OAuth2Request
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        //通过OAuth2Request 创建
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        //交由authorizationServerTokenServices 创建token得到令牌
        OAuth2AccessToken oAuth2AccessToken = myDefaultTokenServices.createAccessToken(oAuth2Authentication);
        Map<String, Object> additionalInformation = new HashMap<>(16);
        additionalInformation.put("userInfo", principal);
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInformation);
        response.getWriter().write(objectMapper.writeValueAsString(oAuth2AccessToken));
    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
            throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }

    public OAuth2AccessToken refresh(String refreshToken, String head) throws IOException {
        String[] tokens = extractAndDecodeHeader(head, null);
        assert tokens.length == 2;
        //获取用户名
        String clientId = tokens[0];
        //获取密码
        String clientSecret = tokens[1];
        //根据用户名得到clientDetails
        ClientDetails clientDetails = null;
        try {
            clientDetails = clientDetailsService.loadClientByClientId(clientId);
        } catch (Exception e) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配" + clientId);
        }
        //验证clientDetails
        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("client配置不存在" + clientId);
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配" + clientId);
        }
        /**第一套方案（基于redis生成token返回用户标识）*/
        //创建一个TokenRequest,空MAP、clientId、Scope具备权限、custom自定义grantType标识
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_SORTED_MAP, clientId, clientDetails.getScope(), "custom");

        OAuth2AccessToken oAuth2AccessToken = myDefaultTokenServices.refreshAccessToken(refreshToken, tokenRequest);
        OAuth2Authentication authentication = tokenStore.readAuthenticationForRefreshToken(oAuth2AccessToken.getRefreshToken());
        Object principal = authentication.getUserAuthentication().getPrincipal();
        Map<String, Object> additionalInformation = new HashMap<>(16);
        additionalInformation.put("userInfo", principal);
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInformation);
		tokenStore.storeAccessToken(oAuth2AccessToken, authentication);
        return oAuth2AccessToken;
    }

    public void deleteOauth2AccessToken(String refreshToken) {
        OAuth2RefreshToken oAuth2RefreshToken = new DefaultOAuth2RefreshToken(refreshToken);
		tokenStore.removeAccessTokenUsingRefreshToken(oAuth2RefreshToken);
    }
}
