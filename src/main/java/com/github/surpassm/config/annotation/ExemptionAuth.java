package com.github.surpassm.config.annotation;

import java.lang.annotation.*;

/**
 * @author mc
 * Create date 2019/9/6 14:31
 * Version 1.0
 * Description 接口加入免验证队列中 surpassm:security:o-auth2:no-verify[0]
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD,ElementType.TYPE})
public @interface ExemptionAuth {
}
