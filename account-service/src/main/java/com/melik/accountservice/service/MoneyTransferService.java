package com.melik.accountservice.service;

import com.melik.accountservice.dto.MoneyTransferDto;
import com.melik.accountservice.dto.MoneyTransferSaveDto;

/**
 * @Author mselvi
 * @Created 16.09.2023
 */

public interface MoneyTransferService {

    MoneyTransferDto transferMoney(MoneyTransferSaveDto moneyTransferSaveDto);
}
