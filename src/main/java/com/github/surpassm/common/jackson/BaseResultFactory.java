package com.github.surpassm.common.jackson;

import java.util.List;

/**
 * @author mc
 * Create date 2019/6/24 17:32
 * Version 1.0
 * Description
 */
public class BaseResultFactory<T extends AbstractBaseDomain> {

	private static final String LOGGER_LEVEL_DEBUG = "DEBUG";

	private static BaseResultFactory baseResultFactory;

	private BaseResultFactory() {

	}

	public static BaseResultFactory getInstance() {
		if (baseResultFactory == null) {
			synchronized (BaseResultFactory.class) {
				if (baseResultFactory == null) {
					baseResultFactory = new BaseResultFactory();
				}
			}
		}
		return baseResultFactory;
	}

	/**
	 * 构建单笔数据结果集
	 *
	 * @param self
	 * @return
	 */
	public AbstractBaseResult build(String self, T attributes) {
		return new SuccessResult<>(self, attributes);
	}

	/**
	 * 构建多笔数据结果集
	 *
	 * @param self
	 * @param next
	 * @param last
	 * @return
	 */
	public AbstractBaseResult build(String self, int next, int last, List<T> attributes) {
		return new SuccessResult<>(self, next, last, attributes);
	}

	/**
	 * 构建请求错误的响应结构
	 *
	 * @param code
	 * @param title
	 * @param detail
	 * @param level  日志级别，只有 DEBUG 时才显示详情
	 * @return
	 */
	public AbstractBaseResult build(Integer code, String title, String detail, String level) {
		if (LOGGER_LEVEL_DEBUG.equals(level)) {
			return new ErrorResult(code, title, detail);
		} else {
			return new ErrorResult(code, title, null);
		}
	}
}
