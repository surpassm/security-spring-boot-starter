package com.github.surpassm.security;

import com.github.surpassm.security.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author mc
 * version 1.0v
 * date 2019/2/10 17:16
 * description 使SecurityProperties 读取器生效
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
