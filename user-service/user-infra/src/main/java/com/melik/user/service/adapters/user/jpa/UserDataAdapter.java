package com.melik.user.service.adapters.user.jpa;

import com.melik.user.service.adapters.user.jpa.entity.UserEntity;
import com.melik.user.service.adapters.user.jpa.repository.UserRepository;
import com.melik.user.service.user.entity.User;
import com.melik.user.service.user.port.UserPort;
import com.melik.user.service.user.usecase.UserAuthenticate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

@Service
@RequiredArgsConstructor
public class UserDataAdapter implements UserPort {

    private final UserRepository userRepository;

    @Override
    public Optional<User> retrieveByEmail(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        return userEntityOptional.map(UserEntity::toModel);
    }

    @Override
    public User persist(UserAuthenticate userAuthenticate) {
        var userEntity = new UserEntity();
        userEntity.setEmail(userAuthenticate.getEmail());
        userEntity.setPassword(userAuthenticate.getPassword());
        return userRepository.save(userEntity).toModel();
    }
}
