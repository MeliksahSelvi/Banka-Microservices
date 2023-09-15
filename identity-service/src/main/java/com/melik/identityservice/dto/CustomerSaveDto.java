package com.melik.identityservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@Getter
@Setter
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
