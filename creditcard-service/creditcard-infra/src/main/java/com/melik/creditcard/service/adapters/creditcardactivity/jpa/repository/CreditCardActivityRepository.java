package com.melik.creditcard.service.adapters.creditcardactivity.jpa.repository;

import com.melik.creditcard.service.adapters.creditcardactivity.jpa.entity.CreditCardActivityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

public interface CreditCardActivityRepository extends JpaRepository<CreditCardActivityEntity, Long> {

    Page<CreditCardActivityEntity> findAllByCreditCardIdAndTransactionDateBetween(
            Long creditCardId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable);

    List<CreditCardActivityEntity> findAllByCreditCardIdAndTransactionDateBetween(
            Long creditCardId,
            LocalDateTime startDate,
            LocalDateTime endDate);
}
