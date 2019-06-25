package com.github.surpassm.common.jackson;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author mc
 * Create date 2019/6/24 17:32
 * Version 1.0
 * Description
 */
@SuppressWarnings("all")
public class BaseResultFactory<T extends AbstractBaseDomain> {

	private static final String LOGGER_LEVEL_DEBUG = "DEBUG";

	private static BaseResultFactory baseResultFactory;
	/**
	 * 设置通用的响应
	 */
	private static HttpServletResponse response;

	private BaseResultFactory() {

	}

	public static BaseResultFactory getInstance(HttpServletResponse response) {
		if (baseResultFactory == null) {
			synchronized (BaseResultFactory.class) {
				if (baseResultFactory == null) {
					baseResultFactory = new BaseResultFactory();
				}
			}
		}
		BaseResultFactory.response = response;
		// 设置通用响应
		baseResultFactory.initResponse();
		return baseResultFactory;
	}

	/**
	 * 构建单笔数据结果集
	 * @param self 当前请求url
	 * @param attributes 实体
	 * @return 返回集
	 */
	public AbstractBaseResult build(String self, T attributes) {
		return new SuccessResult<>(self, attributes);
	}

	/**
	 * 构建多笔数据结果集
	 * @param self 当前请求url
	 * @param next 下一条
	 * @param last 最后一条
	 * @param attributes 集合
	 * @return 返回集
	 */
	public AbstractBaseResult build(String self, int next, int last, List<T> attributes) {
		return new SuccessResult<>(self, next, last, attributes);
	}

	/**
	 * 构建请求错误的响应结构
	 *
	 * @param code 错误代码
	 * @param title 错误标题
	 * @param detail 错误详情
	 * @param level  日志级别，只有 DEBUG 时才显示详情
	 * @return 返回集
	 */
	public AbstractBaseResult build(Integer code, String title, String detail, String level) {
		// 设置请求失败的响应码
		response.setStatus(code);
		if (LOGGER_LEVEL_DEBUG.equals(level)) {
			return new ErrorResult(code, title, detail);
		} else {
			return new ErrorResult(code, title, null);
		}
	}

	/**
	 * 初始化 HttpServletResponse
	 */
	private void initResponse() {
		// 需要符合 JSON API 规范
		response.setHeader("Content-Type", "application/vnd.api+json");
	}
}
