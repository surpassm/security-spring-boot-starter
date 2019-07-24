/**
 * 
 */
package com.github.surpassm.security.code.sms;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author Administrator
 */
public interface SmsCodeSender {
	/**
	 * 短信发送
	 * @param request request
	 * @param mobile mobile
	 * @param code code
	 */
	void send(ServletWebRequest request, String mobile, String code);

}
