package com.github.surpassm.common.base;

import com.github.surpassm.common.jackson.AbstractBaseDomain;
import com.github.surpassm.common.jackson.AbstractBaseResult;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.github.surpassm.common.jackson.BaseResultFactory.getInstance;

/**
 * @author mc
 * Create date 2019/6/25 8:58
 * Version 1.0
 * Description
 */
public abstract class AbstractBaseController<T extends AbstractBaseDomain> {
	/**
	 * 用于动态获取配置文件的属性值
	 */
	private static final String ENVIRONMENT_LOGGING_LEVEL_MY_SHOP = "logging.level.com.liaoin";

	@Resource
	protected HttpServletRequest request;
	@Resource
	private HttpServletResponse response;

	@Resource
	private ConfigurableApplicationContext applicationContext;

	@ModelAttribute
	public void initReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	/**
	 * 请求成功
	 * @param self 当前请求URL
	 * @param attribute 返回实体
	 * @return 结果集
	 *
	 */
	protected AbstractBaseResult success(String self, T attribute) {
		return getInstance(response).build(self, attribute);
	}

	/**
	 * 请求成功
	 * @param self 当前请求URL
	 * @param next 下一条数据URL
	 * @param last 最后一条数据URL
	 * @param attributes 返回集合
	 * @return 结果集
	 */
	protected AbstractBaseResult success(String self, int next, int last, List<T> attributes) {
		return getInstance(response).build(self, next, last, attributes);
	}

	/**
	 * 请求失败
	 * @param title 错误标题
	 * @param detail 错误详情
	 * @return 结果集
	 */
	protected AbstractBaseResult error(String title, String detail) {
		return error(HttpStatus.UNAUTHORIZED.value(), title, detail);
	}

	/**
	 * 请求失败
	 * @param code 错误编码
	 * @param title 错误标题
	 * @param detail 错误详情
	 * @return 结果集
	 */
	protected AbstractBaseResult error(int code, String title, String detail) {
		return getInstance(response).build(code, title, detail, applicationContext.getEnvironment().getProperty(ENVIRONMENT_LOGGING_LEVEL_MY_SHOP));
	}
}
