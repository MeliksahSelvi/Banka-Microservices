package com.melik.account.service.account.usecase;

import com.melik.account.service.account.valueobject.AccountType;
import com.melik.account.service.account.valueobject.CurrencyType;
import com.melik.account.service.common.model.UseCase;
import com.melik.account.service.common.valueobject.Money;
import lombok.Builder;
import lombok.Data;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
public class AccountSave implements UseCase {

    private Long customerId;
    private Money currentBalance;
    private CurrencyType currencyType;
    private AccountType accountType;
}
