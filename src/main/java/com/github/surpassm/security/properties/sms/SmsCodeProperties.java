package com.github.surpassm.security.properties.sms;

import lombok.Data;

/**
 * @author mc
 * @version 1.0
 * 短信验证码属性配置
 */

@Data
public class SmsCodeProperties {
	/**
	 * 长度
	 */
	private int length = 6;
	/**
	 * 过期时间 单位：秒
	 */
	private int expireIn = 60;
	/**
	 * 那些拦截需要验证码
	 */
	private String url;
	/**
	 * 单个手机发送次数限制
	 */
	private int limit = 100;
	/**
	 * 单个手机发送次数限制时长 单位：分钟
	 */
	private int limitDuration = 1440;
	/**
	 * 前端验证发送限制缓存key
	 */
	private String limitKey = "surpassm";
}
