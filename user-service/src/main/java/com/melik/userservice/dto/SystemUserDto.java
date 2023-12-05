package com.melik.userservice.dto;

import lombok.*;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserDto {

    private Long id;
    private String email;
    private Long customerId;
}
