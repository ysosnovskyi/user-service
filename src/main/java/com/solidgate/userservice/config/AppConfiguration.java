package com.solidgate.userservice.config;

import com.solidgate.userservice.config.properties.BatchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({BatchProperties.class})
public class AppConfiguration {

}
