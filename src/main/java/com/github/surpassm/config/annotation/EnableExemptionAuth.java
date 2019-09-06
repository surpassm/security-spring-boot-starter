package com.github.surpassm.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mc
 * Create date 2019/9/6 14:31
 * Version 1.0
 * Description 接口加入免验证队列中 surpassm:security:o-auth2:no-verify[0]
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableExemptionAuth {
}
