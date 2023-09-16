package com.melik.accountservice.service;

import com.melik.accountservice.dto.AccountDto;
import com.melik.accountservice.dto.AccountSaveDto;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 16.09.2023
 */

public interface AccountService {

    List<AccountDto> findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional);

    AccountDto findById(Long accountId);

    AccountDto save(AccountSaveDto accountSaveDto);

    void cancel(Long accountId);
}
