package com.melik.customerservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@Getter
@Setter
@Builder
public class UserDto {

    private Long id;
    private Long identityNo;
    private String password;
}
