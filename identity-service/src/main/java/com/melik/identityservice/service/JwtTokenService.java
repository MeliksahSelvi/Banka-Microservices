package com.melik.identityservice.service;

import com.melik.identityservice.dto.JwtToken;
import org.springframework.security.core.Authentication;

/**
 * @Author mselvi
 * @Created 16.09.2023
 */

public interface JwtTokenService {

    JwtToken genereteJwtToken(Authentication authentication);

    Long findUserIdByToken(String token);

    boolean validateToken(String token);
}
