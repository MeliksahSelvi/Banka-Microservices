package com.melik.user.service.adapters.user.jpa.repository;

import com.melik.user.service.adapters.user.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 06.01.2024
 */


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
}
