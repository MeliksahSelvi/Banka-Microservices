package com.melik.creditcard.service.creditcardactivity;

import com.melik.creditcard.service.common.DomainComponent;
import com.melik.creditcard.service.common.exception.CreditCardDomainBusinessException;
import com.melik.creditcard.service.common.usecase.UseCaseHandler;
import com.melik.creditcard.service.common.valueobject.Money;
import com.melik.creditcard.service.common.valueobject.StatusType;
import com.melik.creditcard.service.creditcard.entity.CreditCard;
import com.melik.creditcard.service.creditcard.port.CreditCardPort;
import com.melik.creditcard.service.creditcardactivity.entity.CreditCardActivity;
import com.melik.creditcard.service.creditcardactivity.port.CreditCardActivityPort;
import com.melik.creditcard.service.creditcardactivity.usecase.CreditCardActivitySpend;
import com.melik.creditcard.service.creditcardactivity.valueobject.CardActivityType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class CreditCardActivitySpendUseCaseHandler implements UseCaseHandler<CreditCardActivity, CreditCardActivitySpend> {

    private final CreditCardPort creditCardPort;
    private final CreditCardActivityPort creditCardActivityPort;

    @Transactional
    @Override
    public CreditCardActivity handle(CreditCardActivitySpend creditCardActivitySpend) {
        Money amount = creditCardActivitySpend.getAmount();
        String description = creditCardActivitySpend.getDescription();

        CreditCard creditCard = getCreditCard(creditCardActivitySpend);

        Money currentDebt = creditCard.getCurrentDebt().add(amount);
        Money currentAvailableLimit = creditCard.getAvailableCardLimit().substract(amount);

        validatedCardLimit(currentAvailableLimit);

        updateCreditCardForSpend(creditCard, currentDebt, currentAvailableLimit);

        return createCreditCardActivity(amount, description, creditCard.getId());
    }

    private CreditCard getCreditCard(CreditCardActivitySpend creditCardActivitySpend) {
        Long cardNo = creditCardActivitySpend.getCardNo();
        Long cvvNo = creditCardActivitySpend.getCvvNo();
        LocalDate expireDate = creditCardActivitySpend.getExpireDate();

        Optional<CreditCard> creditCardOptional = creditCardPort.retrieveByCardNoAndCvvNoAndExpireDateAndStatusType(cardNo, cvvNo, expireDate, StatusType.ACTIVE);

        validateCreditCard(creditCardOptional);

        return creditCardOptional.get();
    }

    private void validateCreditCard(Optional<CreditCard> creditCardOptional) {
        CreditCard creditCard = creditCardOptional.orElseThrow(() -> {
            log.error("Invalid Credit Card!");
            throw new CreditCardDomainBusinessException("Invalid Credit Card!");
        });

        if (creditCard.getExpireDate().isBefore(LocalDate.now())) {
            log.error("This Credit Card Is Expired");
            throw new CreditCardDomainBusinessException("This Credit Card Is Expired");
        }
    }

    private void validatedCardLimit(Money currentAvailableLimit) {
        if (!currentAvailableLimit.isGreaterThanZero()) {
            log.error("This Credit Card Limit Is Insufficient");
            throw new CreditCardDomainBusinessException("This Credit Card Limit Is Insufficient");
        }
    }

    private void updateCreditCardForSpend(CreditCard creditCard, Money currentDebt, Money currentAvailableLimit) {
        creditCard.setCurrentDebt(currentDebt);
        creditCard.setAvailableCardLimit(currentAvailableLimit);
        creditCardPort.update(creditCard);
    }

    private CreditCardActivity createCreditCardActivity(Money amount, String description, Long creditCardId) {

        CreditCardActivity creditCardActivity = CreditCardActivity.builder()
                .creditCardId(creditCardId)
                .amount(amount)
                .description(description)
                .transactionDate(LocalDateTime.now())
                .cardActivityType(CardActivityType.SPEND)
                .build();

        return creditCardActivityPort.save(creditCardActivity);
    }
}
