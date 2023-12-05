package com.melik.creditcardservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author mselvi
 * @Created 05.12.2023
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "creditcard-service")
public class InnerRestCallData {
    private String method;
    private String accept;
    private String uri;
}
