package com.github.surpassm.security.mobile;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.Filter;

/**
 * @author mc
 * Create date 2019/7/13 10:39
 * Version 1.0
 * Description
 */
@Component
public class MobileCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Resource
	private AuthenticationSuccessHandler surpassmAuthenticationSuccessHandler;

	@Resource
	private AuthenticationFailureHandler surpassmAuthenctiationFailureHandler;

	@Resource
	private UserDetailsService userDetailsService;

	@Resource
	private Filter mobileFilter;

	@Override
	public void configure(HttpSecurity http) throws Exception {

		MobileCodeAuthenticationFilter filter = new MobileCodeAuthenticationFilter();
		filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		filter.setAuthenticationSuccessHandler(surpassmAuthenticationSuccessHandler);
		filter.setAuthenticationFailureHandler(surpassmAuthenctiationFailureHandler);

		MobileCodeAuthenticationProvider provider = new MobileCodeAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);

		http.authenticationProvider(provider)
				.addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);

		http.addFilterBefore(mobileFilter, AbstractPreAuthenticatedProcessingFilter.class);

	}
}
