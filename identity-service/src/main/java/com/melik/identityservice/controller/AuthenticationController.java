package com.melik.identityservice.controller;

import com.melik.identityservice.dto.*;
import com.melik.identityservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ApiResponse register(@RequestBody CustomerSaveDto customerSaveDto) {
        CustomerDto customerDto = authenticationService.register(customerSaveDto);
        return ApiResponse.of(HttpStatus.OK, "Register Is Successfully", Map.of("customerDto", customerDto));
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginDto loginDto) {
        JwtToken jwtToken = authenticationService.login(loginDto);
        return ApiResponse.of(HttpStatus.OK, "Login Access Is Successfully", Map.of("jwtToken", jwtToken));
    }

    @GetMapping("/validate/token")
    public ApiResponse validateToken(@RequestBody String token) {
        authenticationService.validateToken(token);
        return ApiResponse.of(HttpStatus.OK, "Token Is Valid");
    }
}
