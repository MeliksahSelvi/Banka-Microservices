package com.melik.userservice.service.impl;

import com.melik.userservice.domain.SystemUser;
import com.melik.userservice.dto.SystemUserDto;
import com.melik.userservice.dto.SystemUserSaveDto;
import com.melik.userservice.enums.ErrorMessage;
import com.melik.userservice.exception.UserException;
import com.melik.userservice.mapper.UserMapper;
import com.melik.userservice.repository.SystemUserRepository;
import com.melik.userservice.security.JwtToken;
import com.melik.userservice.dto.LoginDto;
import com.melik.userservice.service.AuthenticationService;
import com.melik.userservice.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SystemUserDto register(SystemUserSaveDto systemUserSaveDto) {
        checkEmailAlreadyUse(systemUserSaveDto.getEmail());
        SystemUser systemUser = saveUser(systemUserSaveDto);
        SystemUserDto systemUserDto = userMapper.fromEntity(systemUser);
        return systemUserDto;
    }

    private void checkEmailAlreadyUse(String email) {
        boolean exist = systemUserRepository.existsUserByEmail(email);
        if (exist) {
            throw new UserException(ErrorMessage.EMAIL_IS_ALREADY_USE);
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
        validateUser(loginDto);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtToken jwtToken = jwtTokenService.genereteJwtToken(authentication);
        return jwtToken;
    }


    private void validateUser(LoginDto loginDto) {
        boolean customerIsExist = systemUserRepository.existsUserByEmail(loginDto.getEmail());
        if (!customerIsExist) {
            throw new UserException(ErrorMessage.USER_NOT_FOUND);
        }
    }
}
