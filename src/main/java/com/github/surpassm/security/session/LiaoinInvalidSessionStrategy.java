/**
 * 
 */
package com.github.surpassm.security.session;

import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author mc
 * version 1.0v
 * date 2018/9/9 19:18
 * description
 */
public class LiaoinInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

	public LiaoinInvalidSessionStrategy(String invalidSessionUrl) {
		super(invalidSessionUrl);
	}

	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		onSessionInvalid(request, response);
	}

}
