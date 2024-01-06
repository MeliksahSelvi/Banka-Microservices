package com.melik.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@Component
public class RouterValidator {

    private static final List<String> openApiEndpoints = List.of(
            "/user-service/api/v1/auth/login",
            "/user-service/api/v1/auth/register",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**"
    );


    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
