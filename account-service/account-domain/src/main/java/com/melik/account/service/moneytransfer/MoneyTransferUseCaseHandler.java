package com.melik.account.service.moneytransfer;

import com.melik.account.service.accountactivity.entity.AccountActivity;
import com.melik.account.service.accountactivity.usecase.MoneyActivity;
import com.melik.account.service.accountactivity.valueobject.ActivityType;
import com.melik.account.service.common.DomainComponent;
import com.melik.account.service.common.usecase.UseCaseHandler;
import com.melik.account.service.moneytransfer.entity.MoneyTransfer;
import com.melik.account.service.moneytransfer.port.MoneyTransferPort;
import com.melik.account.service.moneytransfer.usecase.MoneyTransferCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class MoneyTransferUseCaseHandler implements UseCaseHandler<MoneyTransfer, MoneyTransferCase> {

    @Qualifier("moneyActivityWithdrawUseCaseHandler")
    private final UseCaseHandler<AccountActivity, MoneyActivity> moneyActivityWithdrawUseCaseHandler;

    @Qualifier("moneyActivityDepositUseCaseHandler")
    private final UseCaseHandler<AccountActivity, MoneyActivity> moneyActivityDepositUseCaseHandler;

    private final MoneyTransferPort moneyTransferPort;

    @Transactional
    @Override
    public MoneyTransfer handle(MoneyTransferCase moneyTransferCase) {

        var moneyActivityOut = MoneyActivity.builder()
                .accountId(moneyTransferCase.getAccountIdFrom())
                .amount(moneyTransferCase.getAmount())
                .activityType(ActivityType.SEND)
                .build();

        moneyActivityWithdrawUseCaseHandler.handle(moneyActivityOut);

        var moneyActivityIn = MoneyActivity.builder()
                .accountId(moneyTransferCase.getAccountIdTo())
                .amount(moneyTransferCase.getAmount())
                .activityType(ActivityType.GET)
                .build();

        moneyActivityDepositUseCaseHandler.handle(moneyActivityIn);

        return moneyTransferPort.persist(moneyTransferCase);
    }
}
