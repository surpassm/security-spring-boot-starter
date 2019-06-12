/**
 * 
 */
package com.github.surpassm.security.code.sms;

import org.springframework.web.context.request.ServletWebRequest;

public interface SmsCodeSender {
	
	void send(ServletWebRequest request, String mobile, String code);

}
