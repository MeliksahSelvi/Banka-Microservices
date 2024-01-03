package com.melik.account.service.adapters.accountactivity.rest.dto;

import com.melik.account.service.accountactivity.entity.AccountActivity;
import com.melik.account.service.accountactivity.valueobject.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountActivityDto {

    private Long accountId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private BigDecimal currentBalance;
    private ActivityType activityType;

    public static AccountActivityDto fromModel(AccountActivity accountActivity) {
        return AccountActivityDto.builder()
                .accountId(accountActivity.getId())
                .amount(accountActivity.getAmount().getAmount())
                .transactionDate(accountActivity.getTransactionDate())
                .currentBalance(accountActivity.getCurrentBalance().getAmount())
                .activityType(accountActivity.getActivityType())
                .build();
    }
}
