package com.melik.account.service.moneytransfer.port;

import com.melik.account.service.moneytransfer.entity.MoneyTransfer;
import com.melik.account.service.moneytransfer.usecase.MoneyTransferCase;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

public interface MoneyTransferPort {

    MoneyTransfer persist(MoneyTransferCase moneyTransferCase);
}
