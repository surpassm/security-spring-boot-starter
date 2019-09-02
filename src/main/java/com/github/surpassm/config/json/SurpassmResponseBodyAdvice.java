package com.github.surpassm.config.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.surpassm.common.jackson.Result;
import com.github.surpassm.config.annotation.SerializedField;
import com.github.surpassm.config.json.codec.AesEncryptUtils;
import com.github.surpassm.security.properties.SecurityProperties;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author mc
 * Create date 2019/4/15 10:33
 * Version 1.0
 * Description 自定义返回数据  实现 ResponseBodyAdvice
 */
@Order(1)
@ControllerAdvice(basePackages = "com.liaoin.demo.controller")
public class SurpassmResponseBodyAdvice implements ResponseBodyAdvice {
	/**
	 * 包含项
	 */
	private String[] includes = {};
	/**
	 * 排除项
	 */
	private String[] excludes = {};
	/**
	 * 是否加密
	 */
	private boolean outEncode = false;
	@Resource
	private ObjectMapper objectMapper;
	@Resource
	private SecurityProperties securityProperties;

	@Override
	public boolean supports(MethodParameter methodParameter, Class aClass) {
		//进入 beforeBodyWrite 方法前的业务判断
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
		//重新初始化为默认值
		includes = new String[]{};
		excludes = new String[]{};
		outEncode = false;
		if(body==null){
			return null;
		}
		if(Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(SerializedField.class)){
			//获取注解配置的包含和去除字段
			SerializedField serializedField = methodParameter.getMethodAnnotation(SerializedField.class);
			if (serializedField == null){
				return body;
			}
			includes = serializedField.includes();
			excludes = serializedField.excludes();
			//是否加密
			outEncode = serializedField.outEncode();
			if (body instanceof Result){
				Result result = (Result) body;
				Object data = result.getData();
				try {
					String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
					if (!StringUtils.hasText(securityProperties.getEncryptKey())) {
						throw new NullPointerException("请配置surpassm.security.encrypt-key");
					}
					result.setData(AesEncryptUtils.aesEncrypt(content, securityProperties.getEncryptKey()));
					return result;
				}  catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return body;
	}
}
