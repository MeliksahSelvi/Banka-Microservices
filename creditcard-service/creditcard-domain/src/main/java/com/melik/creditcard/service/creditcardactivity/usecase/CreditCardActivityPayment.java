package com.melik.creditcard.service.creditcardactivity.usecase;

import com.melik.creditcard.service.common.model.UseCase;
import com.melik.creditcard.service.common.valueobject.Money;
import lombok.Builder;
import lombok.Data;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
public class CreditCardActivityPayment implements UseCase {
    private Long creditCardId;
    private Money amount;
}
