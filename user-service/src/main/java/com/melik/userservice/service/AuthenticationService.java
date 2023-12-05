package com.melik.userservice.service;

import com.melik.common.module.dto.JwtToken;
import com.melik.userservice.dto.SystemUserDto;
import com.melik.userservice.dto.SystemUserSaveDto;
import com.melik.userservice.dto.LoginDto;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

public interface AuthenticationService {

    SystemUserDto register(SystemUserSaveDto systemUserSaveDto);

    JwtToken login(LoginDto loginDto);
}
