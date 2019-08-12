package com.github.surpassm;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author mc
 * Create date 2019/7/31 12:49
 * Version 1.0
 * Description
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ComponentScan("com.github.surpassm.security")
public @interface EnableSecurity {
}
