package com.melik.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.melik.common.module.dto.JwtToken;
import com.melik.userservice.dto.LoginDto;
import com.melik.userservice.dto.SystemUserDto;
import com.melik.userservice.dto.SystemUserSaveDto;
import com.melik.userservice.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth", produces = "application/vnd.api.v1+json")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity userRegister(@RequestBody @Valid SystemUserSaveDto systemUserSaveDto) {
        SystemUserDto systemUserDto = authenticationService.register(systemUserSaveDto);
        return ResponseEntity.ok(systemUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity userLogin(@RequestBody LoginDto loginDto) throws JsonProcessingException {
        JwtToken jwtToken = authenticationService.login(loginDto);
        return ResponseEntity.ok(jwtToken);
    }
}
