package com.melik.apigateway.filter;

import com.melik.apigateway.enums.ErrorMessage;
import com.melik.apigateway.exception.RequestException;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouterValidator validator;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (validator.isSecured.test(request)) {

                validateRequestHeader(request);

                String token = getToken(request);

                if (StringUtils.hasText(token)) {
                    validateTokenFromIdentityService();
                }
            }
            return chain.filter(exchange);
        });
    }

    private void validateRequestHeader(ServerHttpRequest request) {
        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            throw new RequestException(ErrorMessage.MISS_TOKEN);
        }
    }

    private String getToken(ServerHttpRequest request) {

        String fullToken = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

        String token = null;
        if (StringUtils.hasText(fullToken)) {
            String bearer = "Bearer ";

            if (fullToken.startsWith(bearer)) {
                token = fullToken.substring(bearer.length());
            }
        }
        return token;
    }

    @Retry(name = "identity")
    private void validateTokenFromIdentityService() {
        webClientBuilder.build().get()
                .uri("http://identity-service/auth/validate/token")
                .retrieve();
    }

    public static class Config {

    }
}
