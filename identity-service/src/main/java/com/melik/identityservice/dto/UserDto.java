package com.melik.identityservice.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@Getter
@Setter
public class UserDto {

    private Long id;
    private Long identityNo;
    private String password;
}
