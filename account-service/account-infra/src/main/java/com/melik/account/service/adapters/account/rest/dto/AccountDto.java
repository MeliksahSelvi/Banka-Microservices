package com.melik.account.service.adapters.account.rest.dto;

import com.melik.account.service.account.entity.Account;
import com.melik.account.service.account.valueobject.AccountType;
import com.melik.account.service.account.valueobject.CurrencyType;
import com.melik.account.service.common.valueobject.StatusType;
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
public class AccountDto {

    private Long id;
    private Long customerId;
    private String ibanNo;
    private BigDecimal currentBalance;
    private CurrencyType currencyType;
    private AccountType accountType;
    private StatusType statusType;

    public static AccountDto fromModel(Account account){
        return AccountDto.builder()
                .id(account.getId())
                .customerId(account.getCustomerId())
                .ibanNo(account.getIbanNo())
                .currentBalance(account.getCurrentBalance().getAmount())
                .currencyType(account.getCurrencyType())
                .accountType(account.getAccountType())
                .statusType(account.getStatusType())
                .build();
    }
}
