package com.github.surpassm.security.authorize.impl;

import com.github.surpassm.security.authorize.AuthorizeCofigProvider;
import com.github.surpassm.security.constants.SecurityConstants;
import com.github.surpassm.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author mc
 * version 1.0v
 * date 2018/9/24 21:09
 * description TODO
 */
@Component
@Order(Integer.MIN_VALUE)
public class AuthorizeCofigProviderImpl implements AuthorizeCofigProvider {
    @Resource
    private SecurityProperties securityProperties;
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		//当请求需要身份认证时，默认跳转到controller层的url：/authentication/require
		if (securityProperties.getNoVerify() != null && securityProperties.getNoVerify().length != 0){
			String[] noVerify = securityProperties.getNoVerify();
			for (String url:noVerify){
				config.antMatchers(url).permitAll();
			}
		}
		config.antMatchers(
				SecurityConstants.DEFAULT_UNAUTHENTICATED_URL,
                securityProperties.getLoginPage(),
                //跳转到用户配置的登陆页
                securityProperties.getSignUpUrl(),
                securityProperties.getSessionInvalidUrl()+".json",
                securityProperties.getSessionInvalidUrl()+".html",
                securityProperties.getSignOutUrl()
				)
                .permitAll();

    }
}
