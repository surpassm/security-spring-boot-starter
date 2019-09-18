package com.github.surpassm;

import com.github.surpassm.security.SecurityCoreConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

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
@Import({SecurityCoreConfig.class})
public @interface EnableSecurity {

}
