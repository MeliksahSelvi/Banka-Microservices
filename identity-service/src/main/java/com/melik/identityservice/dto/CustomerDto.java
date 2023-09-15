package com.melik.identityservice.dto;

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
public class CustomerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Long identityNo;
}
