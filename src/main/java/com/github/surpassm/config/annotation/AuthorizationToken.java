package com.github.surpassm.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mc
 * Create date 2019/9/6 14:31
 * Version 1.0
 * Description 用于接收授权token
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorizationToken {
}
