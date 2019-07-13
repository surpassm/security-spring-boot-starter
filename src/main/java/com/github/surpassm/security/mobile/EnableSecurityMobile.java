package com.github.surpassm.security.mobile;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

/**
 * @author mc
 * Create date 2019/7/13 14:52
 * Version 1.0
 * Description
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ComponentScan(value = "com.github.surpassm.security.mobile.*")
public @interface EnableSecurityMobile {
}
