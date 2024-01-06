package com.melik.creditcard.service.creditcard.port;

import com.melik.creditcard.service.common.valueobject.Money;
import com.melik.creditcard.service.common.valueobject.StatusType;
import com.melik.creditcard.service.creditcard.entity.CreditCard;
import com.melik.creditcard.service.creditcard.usecase.CreditCardRetrieve;
import com.melik.creditcard.service.creditcard.usecase.CreditCardRetrieveAll;
import com.melik.creditcard.service.creditcard.usecase.CreditCardSave;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

public interface CreditCardPort {

    List<CreditCard> retrieveAll(CreditCardRetrieveAll creditCardRetrieveAll);

    Optional<CreditCard> retrieve(CreditCardRetrieve creditCardRetrieve);

    CreditCard persist(CreditCardSave creditCardSave, Money limit, LocalDate cutoffDate, LocalDate dueDate);

    CreditCard update(CreditCard creditCard);

    Optional<CreditCard> retrieveByCardNoAndCvvNoAndExpireDateAndStatusType(Long cardNo, Long cvvNo, LocalDate expireDate, StatusType active);
}
