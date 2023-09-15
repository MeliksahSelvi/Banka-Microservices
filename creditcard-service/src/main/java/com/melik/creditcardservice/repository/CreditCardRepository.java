package com.melik.creditcardservice.repository;

import com.melik.creditcardservice.domain.CreditCard;
import com.melik.creditcardservice.dto.CreditCardDetails;
import com.melik.creditcardservice.enums.StatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard,Long> {

    Page<CreditCard> findAllByStatusType(StatusType statusType, Pageable pageable);

    Optional<CreditCard> findByCardNoAndCvvNoAndExpireDateAndStatusType(Long cardNo, Long cvvNo, Date expireDate, StatusType statusType);

    @Query(
            "Select new com.melik.creditcardservice.dto.CreditCardDetails(" +
                    " creditCard.cardNo," +
                    " creditCard.expireDate," +
                    " creditCard.currentDebt," +
                    " creditCard.minimumPaymentAmount," +
                    " creditCard.cutoffDate," +
                    " creditCard.dueDate" +
                    ")" +
                    " From CreditCard creditCard " +
                    " where creditCard.id =:creditCardId"
    )
    CreditCardDetails getCreditCardDetails(Long creditCardId);

}
