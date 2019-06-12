/**
 * 
 */
package com.github.surpassm.security.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author mc
 * version 1.0v
 * date 2018/9/9 19:18
 * description 自定义超时策略
 */
public class LiaoinExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

	public LiaoinExpiredSessionStrategy(String invalidSessionUrl) {
		super(invalidSessionUrl);
	}

	/**
	 * 接口可以取得超时事件或自定义并发登陆处理
	 * @param event
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		onSessionInvalid(event.getRequest(), event.getResponse());
	}
	
	@Override
	protected boolean isConcurrency() {
		return true;
	}

}
