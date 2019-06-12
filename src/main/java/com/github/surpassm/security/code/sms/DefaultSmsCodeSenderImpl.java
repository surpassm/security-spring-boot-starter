/**
 * 
 */
package com.github.surpassm.security.code.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.ServletWebRequest;

@Slf4j
public class DefaultSmsCodeSenderImpl implements SmsCodeSender {

	@Override
	public void send(ServletWebRequest request, String mobile, String code) {
		log.info("手机"+mobile+"发送短信验证码"+code);
	}

}
