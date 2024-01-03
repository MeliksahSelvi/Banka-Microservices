package com.melik.account.service.adapters.account.jpa.repository;

import com.melik.account.service.adapters.account.jpa.entity.AccountEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
