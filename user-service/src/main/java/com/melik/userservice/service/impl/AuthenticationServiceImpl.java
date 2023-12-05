package com.melik.userservice.service.impl;

import com.melik.common.module.dto.JwtToken;
import com.melik.common.module.exception.BankException;
import com.melik.common.module.exception.NotFoundException;
import com.melik.common.module.service.JwtTokenService;
import com.melik.userservice.domain.SystemUser;
import com.melik.userservice.dto.LoginDto;
import com.melik.userservice.dto.SystemUserDto;
import com.melik.userservice.dto.SystemUserSaveDto;
import com.melik.userservice.enums.ErrorMessage;
import com.melik.userservice.mapper.UserMapper;
import com.melik.userservice.repository.SystemUserRepository;
import com.melik.userservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final SystemUserRepository systemUserRepository;
    private final UserMapper userMapper;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SystemUserDto register(SystemUserSaveDto systemUserSaveDto) {
        checkEmailAlreadyUse(systemUserSaveDto.getEmail());
        SystemUser systemUser = saveUser(systemUserSaveDto);
        SystemUserDto systemUserDto = userMapper.fromEntity(systemUser);
        return systemUserDto;
    }

    private void checkEmailAlreadyUse(String email) {
        boolean exist = systemUserRepository.existsByEmail(email);
        if (exist) {
            throw new BankException(ErrorMessage.EMAIL_IS_ALREADY_USE.getMessage());
        }
    }

    private SystemUser saveUser(SystemUserSaveDto systemUserSaveDto) {
        SystemUser systemUser = new SystemUser();
        systemUser.setCustomerId(systemUserSaveDto.getCustomerId());
        systemUser.setEmail(systemUserSaveDto.getEmail());
        String password = passwordEncoder.encode(systemUserSaveDto.getPassword());
        systemUser.setPassword(password);
        return systemUserRepository.save(systemUser);
    }

    @Override
    public JwtToken login(LoginDto loginDto) {
        SystemUser systemUser = validateUser(loginDto);

        JwtToken jwtToken = jwtTokenService.genereteJwtToken(systemUser.getId(),systemUser.getEmail(),systemUser.getPassword());
        return jwtToken;
    }


    private SystemUser validateUser(LoginDto loginDto) {
        Optional<SystemUser> userOptional = systemUserRepository.findByEmail(loginDto.getEmail());
        return userOptional.orElseThrow(() -> {
            throw new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage());
        });
    }
}
