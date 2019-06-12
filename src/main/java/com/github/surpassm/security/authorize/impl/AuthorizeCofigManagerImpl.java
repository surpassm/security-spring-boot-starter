package com.github.surpassm.security.authorize.impl;

import com.github.surpassm.security.authorize.AuthorizeCofigManager;
import com.github.surpassm.security.authorize.AuthorizeCofigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mc
 * version 1.0v
 * date 2018/9/24 21:16
 * description TODO
 */
@Component
public class AuthorizeCofigManagerImpl implements AuthorizeCofigManager {
    @Resource
    private List<AuthorizeCofigProvider> authorizeCofigProviders;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for (AuthorizeCofigProvider authorizeCofigProvider : authorizeCofigProviders){
            authorizeCofigProvider.config(config);
        }
//        config.anyRequest().authenticated();
    }
}
