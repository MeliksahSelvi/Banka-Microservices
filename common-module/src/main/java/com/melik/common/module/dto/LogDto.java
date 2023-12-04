package com.melik.common.module.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

public record LogDto(String message, String description, Date logDate) implements Serializable {
}
