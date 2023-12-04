package com.melik.accountservice.mapper;

import com.melik.accountservice.domain.Account;
import com.melik.accountservice.dto.AccountDto;
import com.melik.accountservice.dto.AccountSaveDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Component
public class AccountMapper {

    public AccountDto fromAccount(Account account) {
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(account, accountDto);
        return accountDto;
    }

    public Account fromSaveDto(AccountSaveDto accountSaveDto) {
        Account account = new Account();
        BeanUtils.copyProperties(accountSaveDto, account);
        return account;
    }
}
