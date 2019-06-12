package com.github.surpassm.security.controller;

import com.github.surpassm.common.jackson.Result;
import com.github.surpassm.security.constants.SecurityConstants;
import com.github.surpassm.security.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author mc
 * Create date 2019/3/11 18:37
 * Version 1.0
 * Description
 */
@Slf4j
@RestController
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class SecurityController {

	/**
	 * 把当前的请求缓存到 session 里去
	 */
	private RequestCache requestCache = new HttpSessionRequestCache();
	/**
	 * 重定向 策略
	 */
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Resource
	private SecurityProperties securityProperties;

	/**
	 * 当需要身份认证时，跳转到这里
	 * @param request request
	 * @param response response
	 * @return SimpleResponse
	 * @throws IOException IOException
	 */
	@RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATED_URL)
	@ResponseStatus(code = HttpStatus.OK)
	public Result requireAuthentication(HttpServletRequest request, HttpServletResponse response)throws IOException {
		//取出引发跳转的请求
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			log.info("引发跳转的请求是:" + targetUrl);
			//判断这个url是否为.html结尾
			if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
				//使用RedirectStrategy接口跳转到配置登陆的url页面，自定义登陆页面
				redirectStrategy.sendRedirect(request, response, securityProperties.getLoginPage());
			}
		}
		return Result.fail(HttpStatus.UNAUTHORIZED.value(),"访问的服务需要身份认证，请刷新TOKEN");
	}
}
