package com.melik.creditcard.service.creditcardactivity.port;

import com.melik.creditcard.service.creditcardactivity.entity.CreditCardActivity;
import com.melik.creditcard.service.creditcardactivity.usecase.CreditCardActivityRetrieve;
import com.melik.creditcard.service.creditcardactivity.usecase.CreditCardActivityRetrieveAll;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

public interface CreditCardActivityPort {

    List<CreditCardActivity> retrieveAll(CreditCardActivityRetrieveAll creditCardActivityRetrieveAll);

    CreditCardActivity save(CreditCardActivity creditCardActivity);

    Optional<CreditCardActivity> retrieve(CreditCardActivityRetrieve creditCardActivityRetrieve);

    List<CreditCardActivity> retrieveAllByCreditCardIdAndTransactionDateBetween(Long cardId, LocalDate termStartDate, LocalDate termEndDate);

}
