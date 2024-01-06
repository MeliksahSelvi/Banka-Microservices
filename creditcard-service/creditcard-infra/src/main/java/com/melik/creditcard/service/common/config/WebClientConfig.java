package com.melik.creditcard.service.common.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @Author mselvi
 * @Created 04.01.2024
 */

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced//birden fazla service instance ile eşleşme durumunda otomatik match işlemi yapması için eklendi.
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
}
