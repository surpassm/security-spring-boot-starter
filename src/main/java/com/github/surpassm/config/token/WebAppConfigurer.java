package com.github.surpassm.config.token;

import com.github.surpassm.config.annotation.EnableExemptionAuth;
import com.github.surpassm.security.properties.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author mc
 * Create date 2019/3/4 15:53
 * Version 1.0
 * Description
 */
@Configuration
@Order(Integer.MAX_VALUE)
public class WebAppConfigurer implements WebMvcConfigurer {
    @Resource
    private TokenMethodArgumentResolver tokenMethodArgumentResolver;
    @Resource
	private TokenInterceptor tokenInterceptor;
    @Resource
	private SecurityProperties securityProperties;
	@Resource
	private RequestMappingHandlerMapping mapping;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(tokenMethodArgumentResolver);
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		getEnableExemptionAuth(securityProperties);
		InterceptorRegistration interceptorRegistration = registry.addInterceptor(tokenInterceptor);
		String[] noVerify = securityProperties.getNoVerify();
		if (noVerify != null && noVerify.length != 0){
			interceptorRegistration.excludePathPatterns(Arrays.asList(noVerify));
		}
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		try {
			String path  = new File("").getCanonicalPath();
			registry.addResourceHandler("/upload/**").addResourceLocations("file:///"+path+"/upload/");
			registry.addResourceHandler("/static/**").addResourceLocations("file:///"+path+"/static/");
		}catch (Exception e){
		}
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Login","Authorization", "Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method","Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
				.allowCredentials(true).maxAge(36000);
	}

	/*private void getEnableExemptionAuth(SecurityProperties securityProperties){
		//新建一个数组
		List<String> exemptionUrl = new ArrayList<>();
		//新增默认排除项
		exemptionUrl.add("/swagger-");
		exemptionUrl.add("/images/");
		exemptionUrl.add("/webjars/");
		exemptionUrl.add("/v2/api-docs");
		exemptionUrl.add("/swagger-resources/configuration/**");
		exemptionUrl.add("/websocket/socketServer.ws");
		exemptionUrl.add("/sockjs/socketServer.ws");
		Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
			RequestMappingInfo info = m.getKey();
			HandlerMethod method = m.getValue();
			Method method1 = method.getMethod();
			Annotation[] annotations = method1.getAnnotations();
			boolean tag = false;
			for (Annotation annotation : annotations) {
				if (annotation.annotationType().isAnnotationPresent(EnableExemptionAuth.class)) {
					tag = true;
				}
			}
			if (tag) {
				PatternsRequestCondition p = info.getPatternsCondition();
				for (String url : p.getPatterns()) {
					exemptionUrl.add(url + "**");
				}
			}
		}
		Object[] objects = exemptionUrl.toArray();
		securityProperties.setNoVerify((String[]) objects);
	}*/
}
