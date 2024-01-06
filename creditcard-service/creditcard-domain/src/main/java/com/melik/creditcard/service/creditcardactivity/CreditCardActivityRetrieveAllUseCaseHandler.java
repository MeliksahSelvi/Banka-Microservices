package com.melik.creditcard.service.creditcardactivity;

import com.melik.creditcard.service.common.DomainComponent;
import com.melik.creditcard.service.common.usecase.UseCaseHandler;
import com.melik.creditcard.service.creditcardactivity.entity.CreditCardActivity;
import com.melik.creditcard.service.creditcardactivity.port.CreditCardActivityPort;
import com.melik.creditcard.service.creditcardactivity.usecase.CreditCardActivityRetrieveAll;
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
public class CreditCardActivityRetrieveAllUseCaseHandler implements UseCaseHandler<List<CreditCardActivity>, CreditCardActivityRetrieveAll> {

    private final CreditCardActivityPort creditCardActivityPort;

    @Override
    public List<CreditCardActivity> handle(CreditCardActivityRetrieveAll creditCardActivityRetrieveAll) {
        List<CreditCardActivity> creditCardActivityList = creditCardActivityPort.retrieveAll(creditCardActivityRetrieveAll);
        log.info("Credit Cards Retrieved for card Id : {}", creditCardActivityRetrieveAll.getCardId());
        return creditCardActivityList;
    }
}
