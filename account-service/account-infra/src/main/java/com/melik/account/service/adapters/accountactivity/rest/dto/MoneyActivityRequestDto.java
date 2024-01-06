package com.melik.account.service.adapters.accountactivity.rest.dto;

import com.melik.account.service.accountactivity.usecase.MoneyActivity;
import com.melik.account.service.accountactivity.valueobject.ActivityType;
import com.melik.account.service.common.valueobject.Money;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyActivityRequestDto {

    @NotNull(message = "Account Id Can Not Be Null")
    @Positive
    private Long accountId;

    @NotNull(message = "Amount Can Not Be Null")
    @Positive
    private BigDecimal amount;

    @NotNull(message = "Activity Type Can Not Be Null")
    private ActivityType activityType;

    public MoneyActivity toModel() {
        return MoneyActivity.builder()
                .accountId(accountId)
                .amount(new Money(amount))
                .activityType(activityType)
                .build();
    }
}
