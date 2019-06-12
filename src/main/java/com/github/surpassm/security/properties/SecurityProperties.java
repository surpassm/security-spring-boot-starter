package com.github.surpassm.security.properties;

import com.github.surpassm.security.constants.SecurityConstants;
import com.github.surpassm.security.enums.LoginResponseType;
import com.github.surpassm.security.properties.app.Oauth2Properties;
import com.github.surpassm.security.properties.sms.SmsCodeProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author mc
 * version 1.0v
 * date 2019/2/10 17:17
 * description TODO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "surpassm.security")
public class SecurityProperties {

    /**
     * 登陆返回方式
     */
    private LoginResponseType loginType = LoginResponseType.JSON;

    /**
     * 不需要验证的url
     */
    private String[] noVerify ={};

    /**
     * 默认的登陆属性名称
     */
    private String usernameParameter = SecurityConstants.DEFAULT_USER_NAME_PARAMETER;
    /**
     * 登陆的密码属性名称
     */
    private String passwordParameter = SecurityConstants.DEFAULT_PASSWORD_PARAMETER;

    /**
     * 当请求需要身份认证时，默认跳转的url
     */
    private String defaultUnauthenticatedUrl = SecurityConstants.DEFAULT_UNAUTHENTICATED_URL;
    /**
     * 默认的用户名密码登录请求处理url
     */
    private String defaultLoginProcessingUrlFrom = SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM;
    /**
     * 默认的退出请求处理url
     */
    private String defaultLoginOutUrl = SecurityConstants.DEFAULT_LOGIN_OUT_URL;

    /**
     * 默认注册页面
     */
    private String signUpUrl = SecurityConstants.DEFAULT_SING_UP_URL;
    /**
     * 默认退出页面
     */
    private String signOutUrl = SecurityConstants.DEFAULT_SING_OUT_URL;
    /**
     * 默认登录页面
     */
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
    /**
     * 记住我的过期时间（单位:秒）
     */
    private int rememberMeSeconds = 3600;

    /**
     * 同一个用户在系统中的最大session数，默认1
     */
    private int maximumSessions = 1;
    /**
     * 达到最大session时是否阻止新的登录请求，默认为false，不阻止，新的登录会将老的登录失效掉
     */
    private boolean maxSessionsPreventsLogin = false;
    /**
     * session失效时跳转的地址
     */
    private String sessionInvalidUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;
	/**
	 * session相关配置
	 */
    private SessionProperties session = new SessionProperties();
	/**
	 * OAuth2相关配置
	 */
    private Oauth2Properties oAuth2 = new Oauth2Properties();
	/**
	 * 短信验证码
	 */
	private SmsCodeProperties sms = new SmsCodeProperties();
	/**
	 * 数据加密在key 长度必须16位
	 */
	private String encryptKey="d7b85f6e214abcda";

}
