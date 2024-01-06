package com.melik.account.service.adapters.account.rest.dto;

import com.melik.account.service.account.usecase.AccountSave;
import com.melik.account.service.account.valueobject.AccountType;
import com.melik.account.service.account.valueobject.CurrencyType;
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
public class AccountSaveDto {

    @NotNull(message = "Customer Id Can Not Be Null")
    @Positive
    private Long customerId;

    @NotNull(message = "Current Balance Can Not Be Null")
    @Positive
    private BigDecimal currentBalance;

    @NotNull(message = "Currency Type Can Not Be Null")
    private CurrencyType currencyType;

    @NotNull(message = "Account Type Can Not Be Null")
    private AccountType accountType;

    public AccountSave toModel() {
        return AccountSave.builder()
                .customerId(customerId)
                .accountType(accountType)
                .currentBalance(new Money(currentBalance))
                .currencyType(currencyType)
                .build();
    }
}
