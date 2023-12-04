package com.melik.userservice.security;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

@Getter
@Setter
public class JwtToken {

    private Long userId;
    private String token;
    private Long tokenIssuedTime;
}
