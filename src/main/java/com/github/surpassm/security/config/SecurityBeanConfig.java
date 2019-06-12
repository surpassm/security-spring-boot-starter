package com.github.surpassm.security.config;

import com.github.surpassm.security.code.sms.DefaultSmsCodeSenderImpl;
import com.github.surpassm.security.code.sms.SmsCodeSender;
import com.github.surpassm.security.logout.LiaoinLogoutSuccessHandler;
import com.github.surpassm.security.properties.SecurityProperties;
import com.github.surpassm.security.session.LiaoinExpiredSessionStrategy;
import com.github.surpassm.security.session.LiaoinInvalidSessionStrategy;
import com.github.surpassm.security.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.annotation.Resource;

/**
 * @author mc
 * version 1.0
 * date 2018/11/6 16:33
 * description
 */
@Configuration
public class SecurityBeanConfig {
	@Resource
	private SecurityProperties securityProperties;

	@Bean
	@ConditionalOnMissingBean(InvalidSessionStrategy.class)
	public InvalidSessionStrategy invalidSessionStrategy(){
		return new LiaoinInvalidSessionStrategy(securityProperties.getSession().getSessionInvalidUrl());
	}

	@Bean
	@ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
		return new LiaoinExpiredSessionStrategy(securityProperties.getSession().getSessionInvalidUrl());
	}

	@Bean
	@ConditionalOnMissingBean(LiaoinLogoutSuccessHandler.class)
	public LogoutSuccessHandler logoutSuccessHandler(){
		return new LiaoinLogoutSuccessHandler(securityProperties);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/**
	 * 如果容器中不存在SmsCodeSender bean就执行该方法
	 * @return SmsCodeSender
	 */
	@Bean
	@ConditionalOnMissingBean(SmsCodeSender.class)
	public SmsCodeSender smsCodeSender() {
		return new DefaultSmsCodeSenderImpl();
	}
}
