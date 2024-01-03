package com.melik.account.service.account;

import com.melik.account.service.account.entity.Account;
import com.melik.account.service.account.port.AccountPort;
import com.melik.account.service.account.usecase.AccountSave;
import com.melik.account.service.common.DomainComponent;
import com.melik.account.service.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Slf4j//todo log
@DomainComponent
@RequiredArgsConstructor
public class AccountSaveUseCaseHandler implements UseCaseHandler<Account, AccountSave> {

    private final AccountPort accountPort;

    @Override
    public Account handle(AccountSave accountSave) {
        Account savedAccount = accountPort.persist(accountSave);
        return savedAccount;
    }
}
