package com.melik.creditcard.service.creditcard;

import com.melik.creditcard.service.common.DomainComponent;
import com.melik.creditcard.service.common.usecase.UseCaseHandler;
import com.melik.creditcard.service.creditcard.entity.CreditCard;
import com.melik.creditcard.service.creditcard.port.CreditCardPort;
import com.melik.creditcard.service.creditcard.usecase.CreditCardRetrieveAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class CreditCardRetrieveAllUseCaseHandler implements UseCaseHandler<List<CreditCard>, CreditCardRetrieveAll> {

    private final CreditCardPort creditCardPort;

    @Override
    public List<CreditCard> handle(CreditCardRetrieveAll creditCardRetrieveAll) {
        List<CreditCard> creditCardList = creditCardPort.retrieveAll(creditCardRetrieveAll);
        log.info("CreditCards Retrieved for page and size values : {} , {}",creditCardRetrieveAll.getPageOptional(),creditCardRetrieveAll.getSizeOptional());
        return creditCardList;
    }
}
