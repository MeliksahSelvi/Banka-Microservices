package com.melik.userservice.service.impl;

import com.melik.userservice.domain.SystemUser;
import com.melik.userservice.repository.SystemUserRepository;
import com.melik.userservice.security.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SystemUserRepository systemUserRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        SystemUser customer = systemUserRepository.findByEmail(email);

        JwtUserDetails userDetails = JwtUserDetails.create(customer);
        return userDetails;
    }

}
