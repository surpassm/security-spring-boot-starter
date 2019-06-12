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

    private int accessTokenValiditySeconds = 7200;
}
