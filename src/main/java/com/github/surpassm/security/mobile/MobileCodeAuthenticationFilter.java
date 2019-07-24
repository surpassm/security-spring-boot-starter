package com.github.surpassm.security.mobile;

import com.github.surpassm.security.constants.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mc
 * Create date 2019/7/13 10:17
 * Version 1.0
 * Description :
 * 与UsernamePasswordAuthenticationProcessingFilter内容一致，
 * 同样继承AbstractAuthenticationProcessingFilter
 */
public class MobileCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	/**
	 * 指定当前过滤器只处理POST请求
	 */
	private boolean postOnly = true;

	public MobileCodeAuthenticationFilter() {
		super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, "POST"));
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		String post = "POST";
		if (postOnly && !post.equals(request.getMethod())) {
			throw new AuthenticationServiceException("认证请求不支持: " + request.getMethod());
		}
		//获取手机号  在请求中，携带参数的名字
		String mobile = request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);

		if (mobile == null) {
			mobile = "";
		}
		//去空格
		mobile = mobile.trim();
		//实例化自定义token处理类
		MobileCodeAuthenticationToken authRequest = new MobileCodeAuthenticationToken(mobile);
		//调用spring AuthenticationManager处理
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

		return this.getAuthenticationManager().authenticate(authRequest);
	}
}
