package com.melik.customer.service.adapters.customer.rest.dto;

import com.melik.customer.service.customer.usecase.CustomerSave;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author mselvi
 * @Created 05.01.2024
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
    @Positive
    private Long identityNo;

    @NotEmpty(message = "Password Can Not Be Empty")
    private String password;

    public CustomerSave toModel(){
        return CustomerSave.builder()
                .firstName(firstName)
                .lastName(lastName)
                .identityNo(identityNo)
                .password(password)
                .build();
    }
}
