package com.melik.apigateway.config;

import com.melik.apigateway.filter.AuthenticationFilter;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;
    private final GatewayServiceConfigData gatewayServiceConfigData;

    @Bean
    Customizer<ReactiveResilience4JCircuitBreakerFactory> circuitBreakerFactoryCustomizer() {
        return reactiveResilience4JCircuitBreakerFactory ->
                reactiveResilience4JCircuitBreakerFactory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                        .timeLimiterConfig(TimeLimiterConfig.custom()
                                .timeoutDuration(Duration.ofMillis(gatewayServiceConfigData.getTimeoutMs()))
                                .build())
                        .circuitBreakerConfig(CircuitBreakerConfig.custom()
                                .failureRateThreshold(gatewayServiceConfigData.getFailureRateThreshold())
                                .slowCallRateThreshold(gatewayServiceConfigData.getSlowCallRateThreshold())
                                .slowCallDurationThreshold(Duration.ofMillis(gatewayServiceConfigData.getSlowCallDurationThreshold()))
                                .permittedNumberOfCallsInHalfOpenState(gatewayServiceConfigData.getPermittedNumOfCallsInHalfOpenState())
                                .slidingWindowSize(gatewayServiceConfigData.getSlidingWindowSize())
                                .minimumNumberOfCalls(gatewayServiceConfigData.getMinNumberOfCalls())
                                .waitDurationInOpenState(Duration.ofMillis(gatewayServiceConfigData.getWaitDurationInOpenState()))
                                .build())
                        .build());

    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/user-service/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://user-service"))
                .route("account-service", r -> r.path("/account-service/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://account-service"))
                .route("creditcard-service", r -> r.path("/creditcard-service/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://creditcard-service"))
                .route("customer-service", r -> r.path("/customer-service/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://customer-service"))
                .build();
    }

}
