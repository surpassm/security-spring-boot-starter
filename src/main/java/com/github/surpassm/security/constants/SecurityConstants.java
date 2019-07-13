package com.github.surpassm.security.constants;

/**
 * @author mc
 * version 1.0
 * date 2018/8/21 10:05
 * description 常量类
 */
public interface SecurityConstants {
	/**
	 * security默认的登陆属性
	 */
	String DEFAULT_USER_NAME_PARAMETER = "username";
	/**
	 * security默认的密码属性
	 */
	String DEFAULT_PASSWORD_PARAMETER = "password";
	/**
	 * 当请求需要身份认证时，默认跳转的url
	 */
	String DEFAULT_UNAUTHENTICATED_URL = "/authentication/require";
	/**
	 * 默认的用户名密码登录请求处理url
	 */
	String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";
	/**
	 * 默认的退出请求处理url
	 */
	String DEFAULT_LOGIN_OUT_URL = "/authentication/out";
	/**
	 * 默认登录页面
	 */
	String DEFAULT_LOGIN_PAGE_URL = "/liaoin-signIn.html";
	/**
	 * 默认注册页面
	 */
	String DEFAULT_SING_UP_URL = "/liaoin-signUp.html";

	/**
	 * 默认退出页面
	 */
	String DEFAULT_SING_OUT_URL = "/error/404.html";
	/**
	 * session失效跳转
	 */
    String DEFAULT_SESSION_INVALID_URL = "/authentication/mobile";
}
