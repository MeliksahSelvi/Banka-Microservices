package com.melik.creditcard.service.adapters.rest;

import com.melik.creditcard.service.adapters.rest.dto.JwtToken;
import com.melik.creditcard.service.common.exception.CreditCardDomainBusinessException;
import com.melik.creditcard.service.common.util.RedisUtil;
import com.melik.creditcard.service.creditcardactivity.port.RestCallPort;
import com.melik.creditcard.service.creditcardactivity.usecase.CustomerResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @Author mselvi
 * @Created 04.01.2024
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class RestCallAdapter implements RestCallPort {

    @Value("${bank.jwt.token.key}")
    private String tokenKey;

    private final WebClient.Builder webClientBuilder;
    private final RedisUtil redisUtil;

    @Override
    public CustomerResponseDto restCallToCustomerService(Long customerId) {
        JwtToken token = readTokenFromCache();
        CustomerResponseDto customerResponseDto = doRestCallCustomerService(customerId, token);
        log.info("Inner rest call to Customer Service for Customer Informantions");
        return customerResponseDto;
    }

    private CustomerResponseDto doRestCallCustomerService(Long customerId, JwtToken token) {
        return webClientBuilder.build()
                .get()
                .uri("http://customer-service/customer-service/api/v1/customers/{id}", uriBuilder -> uriBuilder.build(customerId))
                .headers(h -> h.setBearerAuth(token.getToken()))
                .headers(h -> h.setContentType(MediaType.APPLICATION_JSON))
                .retrieve()
                .onStatus(
                        s -> s.equals(HttpStatus.UNAUTHORIZED),
                        clientResponse -> Mono.just(new CreditCardDomainBusinessException("Not Authenticated")))
                .onStatus(
                        s -> s.equals(HttpStatus.BAD_REQUEST),
                        clientResponse -> Mono.just(new CreditCardDomainBusinessException(clientResponse.statusCode().toString())))
                .onStatus(
                        s -> s.equals(HttpStatus.INTERNAL_SERVER_ERROR),
                        clientResponse -> Mono.just(new Exception(clientResponse.statusCode().toString())))
                .bodyToMono(CustomerResponseDto.class)
                .block();
    }

    private JwtToken readTokenFromCache() {
        return redisUtil.readValue(tokenKey);
    }
}
