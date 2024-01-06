package com.melik.creditcard.service.creditcardactivity.usecase;

import com.melik.creditcard.service.common.model.UseCase;
import com.melik.creditcard.service.common.valueobject.Money;
import com.melik.creditcard.service.creditcardactivity.entity.CreditCardActivity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
public class CreditCardDetails implements UseCase {
    private final Long cardNo;
    private final LocalDate expireDate;
    private final Money currentDebt;
    private final Money minimumPaymentAmount;
    private final LocalDate cutoffDate;
    private final LocalDate dueDate;
    private String firstName;
    private String lastName;
    private List<CreditCardActivity> creditCardActivityList;
}
