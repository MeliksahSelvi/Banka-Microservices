package com.melik.identityservice.service.impl;

import com.melik.identityservice.dto.*;
import com.melik.identityservice.enums.ErrorMessage;
import com.melik.identityservice.exception.CustomerException;
import com.melik.identityservice.exception.TokenException;
import com.melik.identityservice.service.AuthenticationService;
import com.melik.identityservice.service.JwtTokenService;
import com.melik.identityservice.util.CacheUtil;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final CacheUtil cacheUtil;
    private final WebClient.Builder webClientBuilder;

    @Override
    public CustomerDto register(CustomerSaveDto customerSaveDto) {

        ApiResponse apiResponse = fetchResponseFromCustomerService(customerSaveDto);

        CustomerDto customerDto = getCustomerDtoFromResponse(apiResponse);

        return customerDto;
    }

    @Retry(name = "customer")
    private ApiResponse fetchResponseFromCustomerService(CustomerSaveDto customerSaveDto) {
        ApiResponse apiResponse = webClientBuilder.build().post()
                .uri(uriBuilder -> uriBuilder.path("http://customer-service/api/v1/customer").build())
                .bodyValue(customerSaveDto)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .block();
        return apiResponse;
    }

    private CustomerDto getCustomerDtoFromResponse(ApiResponse apiResponse) {
        Map<?, ?> responseData = apiResponse.getData();
        CustomerDto customerDto = (CustomerDto) responseData.get("customerDto");
        return customerDto;
    }

    @Override
    public JwtToken login(LoginDto loginDto) {
        validateCustomer(loginDto);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getIdentityNo().toString(), loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtToken jwtToken = jwtTokenService.genereteJwtToken(authentication);

        writeTokenToCache(jwtToken);

        return jwtToken;
    }

    private void validateCustomer(LoginDto loginDto) {
        boolean customerIsExist = fetchExistCustomerFromCustomerService(loginDto.getIdentityNo());
        if (!customerIsExist) {
            throw new CustomerException(ErrorMessage.CUSTOMER_NOT_FOUND);
        }
    }

    @Retry(name = "customer")
    private Boolean fetchExistCustomerFromCustomerService(Long identityNo) {
        Boolean isExist = webClientBuilder.build().get()
                .uri(uriBuilder -> uriBuilder.path("http://customer-service/api/v1/customer/exist/{id}").build(identityNo))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return isExist;
    }

    private void writeTokenToCache(JwtToken jwtToken) {
        cacheUtil.writeTokenMap(jwtToken);
    }

    @Override
    public void validateToken(String token) {
        boolean isValid = jwtTokenService.validateToken(token);

        if (Boolean.FALSE.equals(isValid)) {
            throw new TokenException(ErrorMessage.TOKEN_IS_NOT_VALID);
        }
    }
}
