package com.melik.identityservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author mselvi
 * @Created 16.09.2023
 */

public interface JwtUserDetailsService extends UserDetailsService {

    UserDetails loadUserByUserId(Long id);
}
