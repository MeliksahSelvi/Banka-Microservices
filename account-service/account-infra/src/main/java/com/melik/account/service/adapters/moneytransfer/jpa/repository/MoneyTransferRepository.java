package com.melik.account.service.adapters.moneytransfer.jpa.repository;

import com.melik.account.service.adapters.moneytransfer.jpa.entity.MoneyTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

public interface MoneyTransferRepository extends JpaRepository<MoneyTransferEntity,Long> {
}
