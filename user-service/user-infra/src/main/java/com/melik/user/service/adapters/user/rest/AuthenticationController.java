package com.melik.user.service.adapters.user.rest;

import com.melik.user.service.adapters.user.rest.dto.UserAuthenticateDto;
import com.melik.user.service.adapters.user.rest.dto.UserDto;
import com.melik.user.service.common.usecase.UseCaseHandler;
import com.melik.user.service.user.entity.User;
import com.melik.user.service.user.usecase.JwtToken;
import com.melik.user.service.user.usecase.UserAuthenticate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author mselvi
 * @Created 06.01.2024
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {

    private final UseCaseHandler<User, UserAuthenticate> userRegisterUseCaseHandler;
    private final UseCaseHandler<JwtToken, UserAuthenticate> userLoginUseCaseHandler;

    @PostMapping("/register")
    public ResponseEntity<UserDto> userRegister(@RequestBody @Valid UserAuthenticateDto userAuthenticateDto) {
        var user = userRegisterUseCaseHandler.handle(userAuthenticateDto.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDto.fromModel(user));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> userLogin(@RequestBody @Valid UserAuthenticateDto userAuthenticateDto) {
        var jwtToken = userLoginUseCaseHandler.handle(userAuthenticateDto.toModel());
        return ResponseEntity.ok(jwtToken);
    }
}
