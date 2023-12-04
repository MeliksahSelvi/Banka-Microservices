package com.melik.userservice.mapper;

import com.melik.userservice.domain.SystemUser;
import com.melik.userservice.dto.SystemUserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @Author mselvi
 * @Created 02.12.2023
 */

@Component
public class UserMapper {

    public SystemUserDto fromEntity(SystemUser systemUser){
        SystemUserDto systemUserDto =new SystemUserDto();
        BeanUtils.copyProperties(systemUser, systemUserDto);
        return systemUserDto;
    }
}
