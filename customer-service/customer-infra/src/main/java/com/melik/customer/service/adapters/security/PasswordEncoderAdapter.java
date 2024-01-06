package com.melik.customer.service.adapters.security;

import com.melik.customer.service.customer.port.EncryptPasswordPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

@Service
@RequiredArgsConstructor
public class PasswordEncoderAdapter implements EncryptPasswordPort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encrypt(String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return encodedPassword;
    }
}
