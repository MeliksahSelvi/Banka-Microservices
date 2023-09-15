package com.melik.creditcardservice.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@Getter
@Setter
public class CustomerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Long identityNo;
}
