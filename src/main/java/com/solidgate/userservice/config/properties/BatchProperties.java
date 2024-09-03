package com.solidgate.userservice.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("batch")
public class BatchProperties {
    private int size;
}
