package com.github.surpassm.security.properties.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mc
 * @version 1.0v
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Oauth2ClientProperties {

    private String clientId;

    private String clientIdSecret;
	/**
	 * TOKEN 过期时间默认12小时
	 */
    private int accessTokenValiditySeconds = 86400;

	/**
	 * 刷新TOKEN过期时间默认30天
	 */
	private int refreshTokenValiditySeconds = 2592000;
}
