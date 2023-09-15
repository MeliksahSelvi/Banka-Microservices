package com.melik.accountservice.dto;

import com.melik.accountservice.enums.AccountType;
import com.melik.accountservice.enums.CurrencyType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Getter
@Setter
public class AccountSaveDto {

    @NotNull(message = "Customer Id Can Not Be Null")
    private Long customerId;

    @NotNull(message = "Current Balance Can Not Be Null")
    private BigDecimal currentBalance;

    @NotNull(message = "Currency Type Can Not Be Null")
    private CurrencyType currencyType;

    @NotNull(message = "Account Type Can Not Be Null")
    private AccountType accountType;
}
