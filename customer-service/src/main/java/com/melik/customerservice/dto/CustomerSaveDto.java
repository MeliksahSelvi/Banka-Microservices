package com.melik.customerservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSaveDto {

    @NotEmpty(message = "First Name Can Not Be Empty")
    private String firstName;

    @NotEmpty(message = "Last Name Can Not Be Empty")
    private String lastName;

    @NotNull(message = "Identity No Can Not Be Null")
    private Long identityNo;

    @NotEmpty(message = "Password Can Not Be Empty")
    private String password;

}
