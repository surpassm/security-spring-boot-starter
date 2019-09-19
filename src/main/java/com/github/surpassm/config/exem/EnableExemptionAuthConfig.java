package com.github.surpassm.config.exem;

import com.github.surpassm.config.annotation.ExemptionAuth;
import com.github.surpassm.security.properties.SecurityProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author mc
 * Create date 2019/9/9 9:16
 * Version 1.0
 * Description
 */
//@Component
public class EnableExemptionAuthConfig implements BeanPostProcessor  {
	@Resource
	private SecurityProperties securityProperties;


	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		//获取元组信息
		Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
		for (Method method : methods) {
			if (method.isAnnotationPresent(ExemptionAuth.class)){
				Annotation[] annotations = method.getAnnotations();
				boolean tag = false;
				for (Annotation annotation : annotations) {
					if (annotation.annotationType().isAnnotationPresent(ExemptionAuth.class)) {
						tag = true;
					}
				}
			}
		}
		return null;
	}
}
