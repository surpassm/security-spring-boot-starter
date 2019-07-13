package com.github.surpassm.security.mobile;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author mc
 * Create date 2019/7/13 10:31
 * Version 1.0
 * Description 自定义手机登陆效验逻辑
 */
@Data
public class MobileCodeAuthenticationProvider implements AuthenticationProvider {


	private UserDetailsService userDetailsService;

	/**
	 * 效验逻辑
	 * @param authentication 认证
	 * @return 返回认证信息
	 * @throws AuthenticationException 认证异常
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		MobileCodeAuthenticationToken token = (MobileCodeAuthenticationToken)authentication;
		//取得手机号
		UserDetails mobile = userDetailsService.loadUserByUsername((String) token.getPrincipal());
		if (mobile == null) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}
		MobileCodeAuthenticationToken authenticationResult = new MobileCodeAuthenticationToken(mobile, mobile.getAuthorities());
		authenticationResult.setDetails(token.getDetails());
		return authenticationResult;
	}

	/**
	 * 指定自定义处理类
	 * @param authentication 认证泛型
	 * @return 布尔值
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		//判断authentication传进来的是否自定义MobileCodeAuthenticationToken类
		return MobileCodeAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
