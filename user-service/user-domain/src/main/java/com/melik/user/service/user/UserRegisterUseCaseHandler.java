package com.melik.user.service.user;

import com.melik.user.service.common.DomainComponent;
import com.melik.user.service.common.exception.UserDomainBusinessException;
import com.melik.user.service.common.usecase.UseCaseHandler;
import com.melik.user.service.user.entity.User;
import com.melik.user.service.user.port.EncryptPasswordPort;
import com.melik.user.service.user.port.UserPort;
import com.melik.user.service.user.usecase.UserAuthenticate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 06.01.2024
 */

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class UserRegisterUseCaseHandler implements UseCaseHandler<User, UserAuthenticate> {

    private final UserPort userPort;
    private final EncryptPasswordPort encryptPasswordPort;

    @Override
    public User handle(UserAuthenticate userAuthenticate) {
        Optional<User> userOptional = userPort.retrieveByEmail(userAuthenticate.getEmail());
        if (userOptional.isPresent()) {
            log.error("Email already in use: {}",userAuthenticate.getEmail());
            throw new UserDomainBusinessException("Email already in use: "+userAuthenticate.getEmail());
        }
        String encryptedPassword = encryptPasswordPort.encrypt(userAuthenticate.getPassword());
        userAuthenticate.setPassword(encryptedPassword);

        User user = userPort.persist(userAuthenticate);
        return user;
    }
}
