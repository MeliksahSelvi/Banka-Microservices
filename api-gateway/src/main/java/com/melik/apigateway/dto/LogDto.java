package com.melik.apigateway.dto;

import java.util.Date;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

public record LogDto(String message, String description, Date logDate) {
}
