package com.melik.common.module.dto;

import lombok.*;

/**
 * @Author mselvi
 * @Created 05.12.2023
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {

    private Long userId;
    private String token;
    private Long tokenIssuedTime;
}
