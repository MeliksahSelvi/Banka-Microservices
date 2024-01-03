package com.melik.account.service.account;

import com.melik.account.service.account.entity.Account;
import com.melik.account.service.account.port.AccountPort;
import com.melik.account.service.account.usecase.AccountRetrieveAll;
import com.melik.account.service.common.DomainComponent;
import com.melik.account.service.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Slf4j//todo log
@DomainComponent
@RequiredArgsConstructor
public class AccountRetrieveAllUseCaseHandler implements UseCaseHandler<List<Account>, AccountRetrieveAll> {

    private final AccountPort accountPort;

    @Override
    public List<Account> handle(AccountRetrieveAll accountRetrieveAll) {
        List<Account> accountList = accountPort.retrieveAll(accountRetrieveAll);
        return accountList;
    }
}
