package com.melik.userservice.service;

import com.melik.userservice.security.JwtToken;
import org.springframework.security.core.Authentication;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

public interface JwtTokenService {

    JwtToken genereteJwtToken(Authentication authentication);

    String findUserEmailByToken(String token);
}
