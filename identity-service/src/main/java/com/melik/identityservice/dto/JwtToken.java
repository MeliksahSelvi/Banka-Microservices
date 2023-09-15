package com.melik.identityservice.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@Getter
@Setter
public class JwtToken {

    private Long userId;
    private String token;
    private Long tokenIssuedTime;
}
