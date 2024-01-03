package com.melik.account.service.accountactivity.port;

import com.melik.account.service.accountactivity.entity.AccountActivity;
import com.melik.account.service.accountactivity.usecase.MoneyActivity;
import com.melik.account.service.common.valueobject.Money;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

public interface AccountActivityPort {

    AccountActivity persist(MoneyActivity moneyActivity, Money newBalance);
}
