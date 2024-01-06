package com.melik.user.service.common.rest;

import lombok.Builder;
import lombok.Data;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

@Data
@Builder
public class ErrorResponse {
    private final String code;
    private final String message;
}
