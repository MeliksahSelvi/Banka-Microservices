package com.melik.accountservice.service;

import com.melik.accountservice.domain.AccountActivity;
import com.melik.accountservice.dto.AccountActivityDto;
import com.melik.accountservice.dto.MoneyActivityDto;
import com.melik.accountservice.dto.MoneyActivityRequestDto;

/**
 * @Author mselvi
 * @Created 16.09.2023
 */

public interface AccountActivityService {

    AccountActivityDto withdraw(MoneyActivityRequestDto moneyActivityRequestDto);

    AccountActivityDto deposit(MoneyActivityRequestDto moneyActivityRequestDto);

    AccountActivity moneyOut(MoneyActivityDto moneyActivityDto);

    AccountActivity moneyIn(MoneyActivityDto moneyActivityDto);
}
