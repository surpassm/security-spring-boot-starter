package com.github.surpassm.security.config;

import com.github.surpassm.security.jwt.SurpassmJwtEnhancer;
import com.github.surpassm.security.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;

/**
 * @author mc
 * @version 1.0v
 * 改变令牌存储，这里采用redis存储
 */
@Configuration
public class TokenStoreConfig {
    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    @ConditionalOnProperty(prefix = "surpassm.security.oAuth2",name = "storeType",havingValue = "redis",matchIfMissing = true)
    public TokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * 定义jwt
     */
    @Configuration
    @ConditionalOnProperty(prefix = "surpassm.security.oAuth2",name = "storeType",havingValue = "jwt")
    public static class JwtTokenConfig{
        @Resource
        private SecurityProperties securityProperties;
        /**
         * token存储
         * @return
         */
        @Bean
        public TokenStore jwtTokenStore(){
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        /**
         * token处理
         * @return
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            accessTokenConverter.setSigningKey(securityProperties.getOAuth2().getSecretKey());
            return accessTokenConverter;
        }

        /**
         * 往jwt token中加入数据
         * @return
         */
        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer(){
            return new SurpassmJwtEnhancer();
        }

    }
}
