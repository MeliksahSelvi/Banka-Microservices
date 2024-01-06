package com.melik.creditcard.service.adapters.creditcard.jpa.repository;

import com.melik.creditcard.service.adapters.creditcard.jpa.entity.CreditCardEntity;
import com.melik.creditcard.service.common.valueobject.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

public interface CreditCardRepository extends JpaRepository<CreditCardEntity, Long> {

    Optional<CreditCardEntity> findByCardNoAndCvvNoAndExpireDateAndStatusType(Long cardNo, Long cvvNo, LocalDate expireDate, StatusType active);
}
