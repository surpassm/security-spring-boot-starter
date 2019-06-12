package com.github.surpassm.security.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.surpassm.common.jackson.Result;
import com.github.surpassm.security.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mc
 * version 1.0v
 * date 2018/9/10 1:49
 * description 退出成功自定义实现
 */
public class LiaoinLogoutSuccessHandler implements LogoutSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private SecurityProperties securityProperties;

    private ObjectMapper objectMapper = new ObjectMapper();

    public LiaoinLogoutSuccessHandler(SecurityProperties securityProperties){
        this.securityProperties=securityProperties;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        logger.info("退出成功");
        String signOutUrl = securityProperties.getSignOutUrl();
        //判断路径是否为空
        if (StringUtils.isBlank(signOutUrl)){
            //传入JSON
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(new Result(HttpStatus.OK.value(),"",signOutUrl)));
        }else {
            //跳转路径
            httpServletResponse.sendRedirect(signOutUrl);
        }
    }
}
