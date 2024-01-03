package com.melik.account.service.account.port;

import com.melik.account.service.account.entity.Account;
import com.melik.account.service.account.usecase.AccountRetrieve;
import com.melik.account.service.account.usecase.AccountRetrieveAll;
import com.melik.account.service.account.usecase.AccountSave;

import java.util.List;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

public interface AccountPort {

    List<Account> retrieveAll(AccountRetrieveAll accountRetrieveAll);

    Account retrieve(AccountRetrieve accountRetrieve);

    Account persist(AccountSave accountSave);

    void update(Account account);
}
