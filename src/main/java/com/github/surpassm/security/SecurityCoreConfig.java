package com.github.surpassm.security;

import com.github.surpassm.security.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

/**
 * @author mc
 * version 1.0v
 * date 2019/2/10 17:16
 * description 使SecurityProperties 读取器生效
 */
@Configuration
@ComponentScan({"com.github.surpassm"})
@EnableConfigurationProperties(SecurityProperties.class)
@ConditionalOnProperty(prefix = "surpassm.security", name = "enabled", havingValue = "true")
public class SecurityCoreConfig {
}
