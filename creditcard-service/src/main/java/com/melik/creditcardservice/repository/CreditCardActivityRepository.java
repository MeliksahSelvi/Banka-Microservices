package com.melik.creditcardservice.repository;

import com.melik.creditcardservice.domain.CreditCardActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Repository
public interface CreditCardActivityRepository extends JpaRepository<CreditCardActivity, Long> {

    Page<CreditCardActivity> findAllByCreditCardIdAndTransactionDateBetween(Long creditCardId, Date startDate, Date endDate, Pageable pageable);

    List<CreditCardActivity> findAllByCreditCardIdAndTransactionDateBetween(Long creditCardId, Date startDate, Date endDate);
}
