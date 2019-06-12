package com.github.surpassm.security.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author mc
 * version 1.0v
 * date 2018/9/24 21:07
 * description 认证配置提供提供者
 */
public interface AuthorizeCofigProvider {
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
