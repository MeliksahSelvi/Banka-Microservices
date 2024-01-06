package com.melik.creditcard.service.creditcard;

import com.melik.creditcard.service.common.DomainComponent;
import com.melik.creditcard.service.common.exception.CreditCardDomainBusinessException;
import com.melik.creditcard.service.common.usecase.UseCaseHandler;
import com.melik.creditcard.service.creditcard.entity.CreditCard;
import com.melik.creditcard.service.creditcard.port.CreditCardPort;
import com.melik.creditcard.service.creditcard.usecase.CreditCardRetrieve;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class CreditCardRetrieveUseCaseHandler implements UseCaseHandler<CreditCard, CreditCardRetrieve> {

    private final CreditCardPort creditCardPort;

    @Override
    public CreditCard handle(CreditCardRetrieve creditCardRetrieve) {
        Optional<CreditCard> creditCard = creditCardPort.retrieve(creditCardRetrieve);
        if (creditCard.isEmpty()) {
            log.error("Not Found Credit Card By id: {}",creditCardRetrieve.getCreditCardId());
            throw new CreditCardDomainBusinessException("Not Found Credit Card By id: "+creditCardRetrieve.getCreditCardId());
        }
        log.info("Credit Card Retrieve for id:{}", creditCardRetrieve.getCreditCardId());
        return creditCard.get();
    }
}
