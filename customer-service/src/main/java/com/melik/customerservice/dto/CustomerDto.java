package com.melik.customerservice.dto;

import lombok.*;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Long identityNo;
}
