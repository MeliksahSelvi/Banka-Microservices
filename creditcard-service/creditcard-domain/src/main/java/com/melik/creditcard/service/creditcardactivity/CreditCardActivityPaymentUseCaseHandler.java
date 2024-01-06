package com.melik.creditcard.service.creditcardactivity;

import com.melik.creditcard.service.common.DomainComponent;
import com.melik.creditcard.service.common.exception.CreditCardDomainBusinessException;
import com.melik.creditcard.service.common.usecase.UseCaseHandler;
import com.melik.creditcard.service.common.valueobject.Money;
import com.melik.creditcard.service.creditcard.entity.CreditCard;
import com.melik.creditcard.service.creditcard.port.CreditCardPort;
import com.melik.creditcard.service.creditcard.usecase.CreditCardRetrieve;
import com.melik.creditcard.service.creditcardactivity.entity.CreditCardActivity;
import com.melik.creditcard.service.creditcardactivity.port.CreditCardActivityPort;
import com.melik.creditcard.service.creditcardactivity.usecase.CreditCardActivityPayment;
import com.melik.creditcard.service.creditcardactivity.valueobject.CardActivityType;
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
public class CreditCardActivityPaymentUseCaseHandler implements UseCaseHandler<CreditCardActivity, CreditCardActivityPayment> {

    private final CreditCardPort creditCardPort;
    private final CreditCardActivityPort creditCardActivityPort;

    @Transactional
    @Override
    public CreditCardActivity handle(CreditCardActivityPayment creditCardActivityPayment) {
        Long creditCardId = creditCardActivityPayment.getCreditCardId();
        Money amount = creditCardActivityPayment.getAmount();

        updateCreditCardForPayment(creditCardId, amount);

        return createCreditCardActivity(amount, creditCardId);
    }

    private CreditCard updateCreditCardForPayment(Long creditCardId, Money amount) {
        CreditCard creditCard = retrieveCreditCard(creditCardId);

        return addLimitToCard(creditCard, amount);
    }

    private CreditCard retrieveCreditCard(Long creditCardId) {
        Optional<CreditCard> creditCardOptional = creditCardPort.retrieve(getCreditCardRetrieve(creditCardId));
        return creditCardOptional.orElseThrow(() -> {
            log.error("Not Found Credit Card By id: {}",creditCardId);
            throw new CreditCardDomainBusinessException("Not Found Credit Card By id: "+creditCardId);
        });
    }

    private CreditCardRetrieve getCreditCardRetrieve(Long creditCardId) {
        return CreditCardRetrieve.builder().creditCardId(creditCardId).build();
    }

    private CreditCard addLimitToCard(CreditCard creditCard, Money amount) {
        Money currentDebt = creditCard.getCurrentDebt().substract(amount);
        Money currentAvailableLimit = creditCard.getAvailableCardLimit().add(amount);

        creditCard.setCurrentDebt(currentDebt);
        creditCard.setAvailableCardLimit(currentAvailableLimit);
        return creditCardPort.update(creditCard);
    }

    private CreditCardActivity createCreditCardActivity(Money amount, Long creditCardId) {
        CreditCardActivity creditCardActivity = CreditCardActivity.builder()
                .creditCardId(creditCardId)
                .amount(amount)
                .description("Payment")
                .transactionDate(LocalDateTime.now())
                .cardActivityType(CardActivityType.PAYMENT)
                .build();
        return creditCardActivityPort.save(creditCardActivity);
    }
}
