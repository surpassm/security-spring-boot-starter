package com.github.surpassm.security.mobile;

import com.github.surpassm.security.constants.SecurityConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author mc
 * Create date 2019/7/13 11:08
 * Version 1.0
 * Description
 */
@Component
public class MobileFilter extends OncePerRequestFilter implements InitializingBean {
	/**
	 * 验证码校验失败处理器
	 */
	@Resource
	private AuthenticationFailureHandler authenticationFailureHandler;
	/**
	 * 存放所有需要校验验证码的url
	 */
	private Map<String, String> urlMap = new HashMap<>(2);

	/**
	 * 验证请求url与配置的url是否匹配的工具类
	 */
	private AntPathMatcher pathMatcher = new AntPathMatcher();

	@Resource
	private MobileProcessorService mobileProcessorService;
	/**
	 * 初始化要拦截的url配置信息
	 * @throws ServletException e
	 */
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, "PHONE");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String type = getValidateCodeType(request);
		if (type != null){
			logger.info("校验请求(" + request.getRequestURI() + ")中的效验手机号类型：" + type);
			try {
				mobileProcessorService.validatePhone(new ServletWebRequest(request, response));
				logger.info("校验通过");
				//捕获手机认证异常
			} catch (MobileException exception) {
				//自定义异常
				authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * 获取校验码的类型，如果当前请求不需要校验，则返回null
	 */
	private String getValidateCodeType(HttpServletRequest request) {
		String result = null;
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
			Set<String> urls = urlMap.keySet();
			for (String url : urls) {
				if (pathMatcher.match(url, request.getRequestURI())) {
					result = urlMap.get(url);
				}
			}
		}
		return result;
	}
}
