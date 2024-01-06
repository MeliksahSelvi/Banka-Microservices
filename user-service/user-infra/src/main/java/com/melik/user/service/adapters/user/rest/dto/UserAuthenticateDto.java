package com.melik.user.service.adapters.user.rest.dto;

import com.melik.user.service.user.usecase.UserAuthenticate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author mselvi
 * @Created 06.01.2024
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticateDto {

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email. Please enter a valid email address")
    private String email;

    @NotEmpty(message = "Password Can Not Be Empty")
    private String password;

    public UserAuthenticate toModel(){
        return UserAuthenticate.builder()
                .email(email)
                .password(password)
                .build();
    }
}
