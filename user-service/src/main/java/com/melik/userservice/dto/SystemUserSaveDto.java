package com.melik.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserSaveDto {

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email. Please enter a valid email address")
    private String email;

    @NotEmpty(message = "Password Can Not Be Empty")
    private String password;

    private Long customerId;
}
