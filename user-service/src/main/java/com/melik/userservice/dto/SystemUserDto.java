package com.melik.userservice.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

@Getter
@Setter
public class SystemUserDto {

    private Long id;
    private String email;
    private Long customerId;
}
