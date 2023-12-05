package com.melik.common.module.service;

import com.melik.common.module.dto.JwtToken;

/**
 * @Author mselvi
 * @Created 05.12.2023
 */

public interface JwtTokenService {

    JwtToken genereteJwtToken(Long id,String email, String password);

    String findUserDetailAsStrByToken(String token);
}
