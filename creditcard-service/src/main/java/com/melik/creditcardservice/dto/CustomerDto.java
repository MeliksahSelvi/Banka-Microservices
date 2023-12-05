package com.melik.creditcardservice.dto;

import lombok.*;

/**
 * @Author mselvi
 * @Created 11.09.2023
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
