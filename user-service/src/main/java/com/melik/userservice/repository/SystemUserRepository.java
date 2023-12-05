package com.melik.userservice.repository;

import com.melik.userservice.domain.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {

    Optional<SystemUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
