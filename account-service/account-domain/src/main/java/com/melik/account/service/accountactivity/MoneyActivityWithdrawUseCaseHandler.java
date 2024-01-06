package com.melik.account.service.accountactivity;

import com.melik.account.service.account.port.AccountPort;
import com.melik.account.service.account.usecase.AccountRetrieve;
import com.melik.account.service.accountactivity.entity.AccountActivity;
import com.melik.account.service.accountactivity.port.AccountActivityPort;
import com.melik.account.service.accountactivity.usecase.MoneyActivity;
import com.melik.account.service.common.DomainComponent;
import com.melik.account.service.common.exception.AccountDomainBusinessException;
import com.melik.account.service.common.usecase.UseCaseHandler;
import com.melik.account.service.common.valueobject.Money;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class MoneyActivityWithdrawUseCaseHandler implements UseCaseHandler<AccountActivity, MoneyActivity> {

    private final AccountPort accountPort;
    private final AccountActivityPort accountActivityPort;

    /*
     * bu method moneytransfer serviceden çağrıldığında yeni bir transaction açacak fakat deposit'den çağrıldığında aynı bean içerisinde olduğu için açmayacak
     * */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    @Override
    public AccountActivity handle(MoneyActivity moneyActivity) {
        var accountFrom = accountPort.retrieve(AccountRetrieve.builder().accountId(moneyActivity.getAccountId()).build());

        Money newBalance = accountFrom.getCurrentBalance().substract(moneyActivity.getAmount());
        validateBalance(newBalance);

        accountFrom.setCurrentBalance(newBalance);
        accountPort.update(accountFrom);

        var accountActivity = accountActivityPort.persist(moneyActivity, newBalance);
        return accountActivity;
    }

    private void validateBalance(Money remainingBalance) {
        if (!remainingBalance.isGreaterThanZero()) {
            log.error("Insufficent Balance During The Withdraw Action!");
            throw new AccountDomainBusinessException("Insufficent Balance During The Withdraw Action!");
        }
    }
}
