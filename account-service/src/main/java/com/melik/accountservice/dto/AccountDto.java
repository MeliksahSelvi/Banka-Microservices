package com.melik.accountservice.dto;

import com.melik.accountservice.enums.AccountType;
import com.melik.accountservice.enums.CurrencyType;
import com.melik.accountservice.enums.StatusType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Getter
@Setter
public class AccountDto {

    private Long id;
    private Long customerId;
    private String ibanNo;
    private BigDecimal currentBalance;
    private CurrencyType currencyType;
    private AccountType accountType;
    private StatusType statusType;
}

