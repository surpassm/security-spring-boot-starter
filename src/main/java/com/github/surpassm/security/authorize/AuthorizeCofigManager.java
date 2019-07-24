package com.github.surpassm.security.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author mc
 * version 1.0v
 * date 2018/9/24 21:16
 * description 认证管理器
 */
public interface AuthorizeCofigManager {
	/**
	 * 认证管理器
	 * @param config s
	 */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
