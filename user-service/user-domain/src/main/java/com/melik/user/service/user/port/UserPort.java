package com.melik.user.service.user.port;

import com.melik.user.service.user.entity.User;
import com.melik.user.service.user.usecase.UserAuthenticate;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

public interface UserPort {

    Optional<User> retrieveByEmail(String email);

    User persist(UserAuthenticate userAuthenticate);
}
