package com.melik.account.service.adapters.accountactivity.jpa;

import com.melik.account.service.accountactivity.entity.AccountActivity;
import com.melik.account.service.accountactivity.port.AccountActivityPort;
import com.melik.account.service.accountactivity.usecase.MoneyActivity;
import com.melik.account.service.adapters.accountactivity.jpa.entity.AccountActivityEntity;
import com.melik.account.service.adapters.accountactivity.jpa.repository.AccountActivityRepository;
import com.melik.account.service.common.valueobject.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Service
@RequiredArgsConstructor
public class AccountActivityDataAdapter implements AccountActivityPort {

    private final AccountActivityRepository accountActivityRepository;

    @Override
    public AccountActivity persist(MoneyActivity moneyActivity, Money newBalance) {
        var accountActivityEntity = new AccountActivityEntity();
        accountActivityEntity.setAccountId(moneyActivity.getAccountId());
        accountActivityEntity.setActivityType(moneyActivity.getActivityType());
        accountActivityEntity.setAmount(moneyActivity.getAmount().getAmount());
        accountActivityEntity.setCurrentBalance(newBalance.getAmount());
        accountActivityEntity.setTransactionDate(LocalDateTime.now());
        return accountActivityRepository.save(accountActivityEntity).toModel();
    }
}
