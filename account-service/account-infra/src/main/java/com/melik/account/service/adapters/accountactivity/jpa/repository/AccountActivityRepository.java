package com.melik.account.service.adapters.accountactivity.jpa.repository;

import com.melik.account.service.adapters.accountactivity.jpa.entity.AccountActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

public interface AccountActivityRepository extends JpaRepository<AccountActivityEntity, Long> {
}
