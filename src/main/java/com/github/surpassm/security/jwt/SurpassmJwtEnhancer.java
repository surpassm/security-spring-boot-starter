package com.github.surpassm.security.jwt;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


/**
 * @author mc
 * @version 1.0v
 */
public class SurpassmJwtEnhancer extends JwtAccessTokenConverter{
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
		return super.enhance(oAuth2AccessToken, oAuth2Authentication);
    }
}
