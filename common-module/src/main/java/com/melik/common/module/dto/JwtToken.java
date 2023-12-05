package com.melik.common.module.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author mselvi
 * @Created 05.12.2023
 */

@Getter
@Setter
public class JwtToken {

    private Long userId;
    private String token;
    private Long tokenIssuedTime;
}
