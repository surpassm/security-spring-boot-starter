package com.github.surpassm.security.properties;

import com.github.surpassm.security.constants.SecurityConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mc
 * @version 1.0
 * @date 2018/8/21 10:03
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionProperties {
	/**
	 * 同一个用户在系统中的最大session数，默认1
	 */
	private int maximumSessions = 1;
	/**
	 * 达到最大session时是否阻止新的登录请求，默认为false，不阻止，新的登录会将老的登录失效掉
	 */
	private boolean maxSessionsPreventsLogin = false;
	/**
	 * session失效时跳转的地址
	 */
	private String sessionInvalidUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;
}
