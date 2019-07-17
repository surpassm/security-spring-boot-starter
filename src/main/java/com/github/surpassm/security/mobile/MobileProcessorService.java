package com.github.surpassm.security.mobile;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author mc
 * Create date 2019/7/13 11:26
 * Version 1.0
 * Description
 */
public interface MobileProcessorService {
	/**
	 * 校验手机号
	 * @param servletWebRequest servletWebRequest
	 */
	void validatePhone(ServletWebRequest servletWebRequest);
}
