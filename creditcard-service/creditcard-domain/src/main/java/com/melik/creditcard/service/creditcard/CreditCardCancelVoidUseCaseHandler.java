package com.melik.creditcard.service.creditcard;

import com.melik.creditcard.service.common.DomainComponent;
import com.melik.creditcard.service.common.exception.CreditCardDomainBusinessException;
import com.melik.creditcard.service.common.usecase.VoidUseCaseHandler;
import com.melik.creditcard.service.common.valueobject.StatusType;
import com.melik.creditcard.service.creditcard.entity.CreditCard;
import com.melik.creditcard.service.creditcard.port.CreditCardPort;
import com.melik.creditcard.service.creditcard.usecase.CreditCardRetrieve;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class CreditCardCancelVoidUseCaseHandler implements VoidUseCaseHandler<CreditCardRetrieve> {

    private final CreditCardPort creditCardPort;

    @Transactional
    @Override
    public void handle(CreditCardRetrieve creditCardRetrieve) {
        Optional<CreditCard> creditCardOptional = creditCardPort.retrieve(creditCardRetrieve);
        CreditCard creditCard = creditCardOptional.orElseThrow(() -> new CreditCardDomainBusinessException("Not Found Credit Card"));
        creditCard.setStatusType(StatusType.PASSIVE);
        creditCard.setCancelDate(LocalDateTime.now());
        creditCardPort.update(creditCard);
        log.info("Credit Card Retrieved by id: {}",creditCardRetrieve.getCreditCardId());
    }
}
