package com.melik.creditcard.service.creditcard;

import com.melik.creditcard.service.common.DomainComponent;
import com.melik.creditcard.service.common.usecase.UseCaseHandler;
import com.melik.creditcard.service.common.valueobject.Money;
import com.melik.creditcard.service.creditcard.entity.CreditCard;
import com.melik.creditcard.service.creditcard.port.CreditCardPort;
import com.melik.creditcard.service.creditcard.usecase.CreditCardSave;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.Month;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class CreditCardSaveUseCaseHandler implements UseCaseHandler<CreditCard, CreditCardSave> {

    private final CreditCardPort creditCardPort;

    @Override
    public CreditCard handle(CreditCardSave creditCardSave) {
        Money limit = calculateLimit(creditCardSave.getEarning());

        LocalDate cutoffDate = getCutoffDate(creditCardSave.getCutoffDay());
        LocalDate dueDate = getDueDate(cutoffDate);
        CreditCard creditCard = creditCardPort.persist(creditCardSave, limit, cutoffDate, dueDate);
        log.info("Credit Card Saved for customer id:{}", creditCardSave.getCustomerId());
        return creditCard;
    }

    private Money calculateLimit(Money earning) {
        return earning.multiply(3);
    }

    private LocalDate getCutoffDate(String cutoffDayStr) {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        Month nextMonth = Month.of(currentMonth).plus(1);

        Integer cutoffDay = getCutoffDay(cutoffDayStr);
        return LocalDate.of(currentYear, nextMonth, cutoffDay);
    }

    private Integer getCutoffDay(String cutoffDayStr) {
        if (!StringUtils.hasText(cutoffDayStr)) {
            cutoffDayStr = "1";
        }
        return Integer.valueOf(cutoffDayStr);
    }

    private LocalDate getDueDate(LocalDate cutoffDateLocal) {
        return cutoffDateLocal.plusDays(10);
    }
}
