package com.melik.account.service.account;

import com.melik.account.service.account.entity.Account;
import com.melik.account.service.account.port.AccountPort;
import com.melik.account.service.account.usecase.AccountRetrieve;
import com.melik.account.service.common.DomainComponent;
import com.melik.account.service.common.usecase.VoidUseCaseHandler;
import com.melik.account.service.common.valueobject.StatusType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Slf4j//todo log
@DomainComponent
@RequiredArgsConstructor
public class AccountCancelVoidUseCaseHandler implements VoidUseCaseHandler<AccountRetrieve> {

    private final AccountPort accountPort;

    @Transactional
    @Override
    public void handle(AccountRetrieve accountRetrieve) {
        Account account = accountPort.retrieve(accountRetrieve);
        account.setStatusType(StatusType.PASSIVE);
        account.setCancelDate(LocalDateTime.now());
        accountPort.update(account);
    }
}
