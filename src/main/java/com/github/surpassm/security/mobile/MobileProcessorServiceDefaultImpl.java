package com.github.surpassm.security.mobile;

import com.github.surpassm.security.constants.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author mc
 * Create date 2019/7/13 11:35
 * Version 1.0
 * Description
 */
@Component
public class MobileProcessorServiceDefaultImpl implements MobileProcessorService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public void validatePhone(ServletWebRequest servletWebRequest) {
		String parameter = servletWebRequest.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
		logger.info("登陆请求验证手机号："+parameter);

	}
}
