package com.melik.creditcard.service.creditcard.usecase;

import com.melik.creditcard.service.common.model.UseCase;
import com.melik.creditcard.service.common.valueobject.Money;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
public class CreditCardSave implements UseCase {
    private Long customerId;
    private Money earning;
    private String cutoffDay;
}
