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
import com.melik.creditcard.service.creditcardactivity.usecase.CreditCardActivityRetrieve;
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
public class CreditCardActivityRefundUseCaseHandler implements UseCaseHandler<CreditCardActivity, CreditCardActivityRetrieve> {

    private final CreditCardPort creditCardPort;
    private final CreditCardActivityPort creditCardActivityPort;

    @Transactional
    @Override
    public CreditCardActivity handle(CreditCardActivityRetrieve creditCardActivityRetrieve) {
        CreditCardActivity oldCreditCardActivity = getCreditCardActivityById(creditCardActivityRetrieve);
        Money amount = oldCreditCardActivity.getAmount();
        String description = "REFUND -> " + oldCreditCardActivity.getDescription();

        CreditCard creditCard = updateCreditCardForRefund(oldCreditCardActivity, amount);

        return createCreditCardActivity(amount, description, creditCard.getId());
    }

    private CreditCardActivity getCreditCardActivityById(CreditCardActivityRetrieve creditCardActivityRetrieve) {
        Optional<CreditCardActivity> activityOptional = creditCardActivityPort.retrieve(creditCardActivityRetrieve);
        return activityOptional.orElseThrow(() -> {
            log.error("Not Found Credit Card Activity By id: {}",creditCardActivityRetrieve.getActivityId());
            throw new CreditCardDomainBusinessException("Not Found Credit Card Activity By id: "+creditCardActivityRetrieve.getActivityId());
        });
    }

    private CreditCard updateCreditCardForRefund(CreditCardActivity oldCreditCardActivity, Money amount) {
        CreditCard creditCard = retrieveCreditCard(oldCreditCardActivity.getCreditCardId());

        return switch (oldCreditCardActivity.getCardActivityType()) {
            case SPEND -> addLimitToCard(creditCard, amount);
            case PAYMENT -> substractLimitToCard(creditCard, amount);
            case REFUND -> creditCard;//todo bir önceki işlem refund ise onu refund etmek nasıl olabilir?event sourcing maybe
        };
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

    private CreditCard substractLimitToCard(CreditCard creditCard, Money amount) {
        Money currentDebt = creditCard.getCurrentDebt().add(amount);
        Money currentAvailableLimit = creditCard.getAvailableCardLimit().substract(amount);

        creditCard.setCurrentDebt(currentDebt);
        creditCard.setAvailableCardLimit(currentAvailableLimit);
        return creditCardPort.update(creditCard);
    }

    private CreditCardActivity createCreditCardActivity(Money amount, String description, Long creditCardId) {
        CreditCardActivity creditCardActivity = CreditCardActivity.builder()
                .creditCardId(creditCardId)
                .amount(amount)
                .description(description)
                .transactionDate(LocalDateTime.now())
                .cardActivityType(CardActivityType.REFUND)
                .build();

        return creditCardActivityPort.save(creditCardActivity);
    }
}
