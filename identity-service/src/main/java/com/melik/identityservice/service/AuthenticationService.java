package com.melik.identityservice.service;

import com.melik.identityservice.dto.CustomerDto;
import com.melik.identityservice.dto.CustomerSaveDto;
import com.melik.identityservice.dto.JwtToken;
import com.melik.identityservice.dto.LoginDto;

/**
 * @Author mselvi
 * @Created 16.09.2023
 */

public interface AuthenticationService {

    CustomerDto register(CustomerSaveDto customerSaveDto);

    JwtToken login(LoginDto loginDto);

    void validateToken(String token);
}
