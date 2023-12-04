package com.melik.accountservice.service.impl;

import com.melik.accountservice.domain.Account;
import com.melik.accountservice.dto.AccountDto;
import com.melik.accountservice.dto.AccountSaveDto;
import com.melik.accountservice.enums.ErrorMessage;
import com.melik.accountservice.enums.StatusType;
import com.melik.accountservice.exception.AccountException;
import com.melik.accountservice.mapper.AccountMapper;
import com.melik.accountservice.repository.AccountRepository;
import com.melik.accountservice.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.melik.common.module.util.Constants.DEFAULT_PAGE;
import static com.melik.common.module.util.Constants.DEFAULT_SIZE;


/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public List<AccountDto> findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        PageRequest pageRequest = getPageRequest(pageOptional, sizeOptional);
        List<Account> accountList = accountRepository.findAll(pageRequest).toList();
        List<AccountDto> accountDtoList = convertListToDtoList(accountList);
        return accountDtoList;
    }

    private PageRequest getPageRequest(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        Integer page = getPageOptionalValue(pageOptional);

        Integer size = getSizeOptionalValue(sizeOptional);

        PageRequest pageRequest = PageRequest.of(page, size);
        return pageRequest;
    }

    private Integer getPageOptionalValue(Optional<Integer> pageOptional) {
        return pageOptional.orElse(DEFAULT_PAGE);
    }

    private Integer getSizeOptionalValue(Optional<Integer> sizeOptional) {
        return sizeOptional.orElse(DEFAULT_SIZE);
    }

    private List<AccountDto> convertListToDtoList(List<Account> customerList) {
        return customerList.stream().map(accountMapper::fromAccount).toList();
    }

    @Override
    public AccountDto findById(Long id) {
        Account accAccount = getAccountById(id);
        AccountDto accAccountDto = accountMapper.fromAccount(accAccount);
        return accAccountDto;
    }

    private Account getAccountById(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);

        return accountOptional.orElseThrow(() -> {
            throw new AccountException(ErrorMessage.ACCOUNT_NOT_FOUND);
        });
    }

    @Override
    public AccountDto save(AccountSaveDto accountSaveDto) {
        Account account = createAccount(accountSaveDto);
        AccountDto accAccountDto = accountMapper.fromAccount(account);
        return accAccountDto;
    }

    private Account createAccount(AccountSaveDto accountSaveDto) {
        String ibanNo = getIbanNo();

        Account accAccount = accountMapper.fromSaveDto(accountSaveDto);
        accAccount.setStatusType(StatusType.ACTIVE);
        accAccount.setCustomerId(accountSaveDto.getCustomerId());
        accAccount.setIbanNo(ibanNo);
        accAccount = accountRepository.save(accAccount);
        return accAccount;
    }

    private String getIbanNo() {
        String ibanNo = RandomStringUtils.randomNumeric(26);
        return ibanNo;
    }

    @Override
    @Transactional
    public void cancel(Long accountId) {
        Account account = getAccountById(accountId);
        account.setStatusType(StatusType.PASSIVE);
        account.setCancelDate(new Date());
        accountRepository.save(account);
    }
}
