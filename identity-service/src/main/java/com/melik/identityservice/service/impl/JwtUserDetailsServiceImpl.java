package com.melik.identityservice.service.impl;

import com.melik.identityservice.dto.UserDto;
import com.melik.identityservice.security.JwtUserDetails;
import com.melik.identityservice.service.JwtUserDetailsService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@Service
@RequiredArgsConstructor
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

    private final WebClient.Builder webClientBuilder;

    @Override
    public UserDetails loadUserByUsername(String identityNo) throws UsernameNotFoundException {
        Long identityNoAsLong = Long.valueOf(identityNo);
        String uri = "http://customer-service/api/v1/customer/user/identityNo/{id}";

        UserDto userDto = fetchUserDtoFromCustomerService(identityNoAsLong, uri);

        JwtUserDetails userDetails = JwtUserDetails.create(userDto);
        return userDetails;
    }

    @Override
    public UserDetails loadUserByUserId(Long id) throws UsernameNotFoundException {
        String uri = "http://customer-service/api/v1/customer/user/{id}";

        UserDto userDto = fetchUserDtoFromCustomerService(id, uri);

        JwtUserDetails userDetails = JwtUserDetails.create(userDto);
        return userDetails;
    }

    @Retry(name = "customer")
    private UserDto fetchUserDtoFromCustomerService(Long id, String uri) {
        return webClientBuilder.build().get()
                .uri(uriBuilder -> uriBuilder.path(uri).build(id))
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }
}
