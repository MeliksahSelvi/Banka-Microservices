package com.melik.customerservice.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Getter
@Setter
public class CustomerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Long identityNo;
}
