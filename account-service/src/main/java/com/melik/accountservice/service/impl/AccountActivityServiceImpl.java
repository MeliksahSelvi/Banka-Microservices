package com.melik.accountservice.service.impl;

import com.melik.accountservice.domain.Account;
import com.melik.accountservice.domain.AccountActivity;
import com.melik.accountservice.dto.AccountActivityDto;
import com.melik.accountservice.dto.MoneyActivityDto;
import com.melik.accountservice.dto.MoneyActivityRequestDto;
import com.melik.accountservice.enums.ActivityType;
import com.melik.accountservice.enums.ErrorMessage;
import com.melik.accountservice.exception.AccountException;
import com.melik.accountservice.exception.MoneyException;
import com.melik.accountservice.mapper.AccountActivityMapper;
import com.melik.accountservice.repository.AccountActivityRepository;
import com.melik.accountservice.repository.AccountRepository;
import com.melik.accountservice.service.AccountActivityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Service
@RequiredArgsConstructor
public class AccountActivityServiceImpl implements AccountActivityService {

    private final AccountRepository accountRepository;
    private final AccountActivityRepository accountActivityRepository;

    @Override
    @Transactional
    public AccountActivityDto withdraw(MoneyActivityRequestDto moneyActivityRequestDto) {
        Long accountId = moneyActivityRequestDto.getAccountId();
        BigDecimal amount = moneyActivityRequestDto.getAmount();

        MoneyActivityDto moneyActivityDto = createMoneyActivityDto(accountId, amount, ActivityType.WITHDRAW);

        AccountActivity accountActivity = moneyOut(moneyActivityDto);

        AccountActivityDto accountActivityDto = AccountActivityMapper.fromAccountActivity(accountActivity);
        return accountActivityDto;
    }

    private MoneyActivityDto createMoneyActivityDto(Long accountId, BigDecimal amount, ActivityType activityType) {
        MoneyActivityDto moneyActivityDto = new MoneyActivityDto();
        moneyActivityDto.setAccountId(accountId);
        moneyActivityDto.setAmount(amount);
        moneyActivityDto.setActivityType(activityType);
        return moneyActivityDto;
    }

    /*
     * bu method moneytransfer serviceden çağrıldığında yeni bir transaction açacak fakat withdraw'den çağrıldığında aynı bean içerisinde olduğu için açmayacak
     * */
    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public AccountActivity moneyOut(MoneyActivityDto moneyActivityDto) {

        Long accountIdFrom = moneyActivityDto.getAccountId();
        BigDecimal amount = moneyActivityDto.getAmount();
        ActivityType activityType = moneyActivityDto.getActivityType();

        Account account = getAccountById(accountIdFrom);

        BigDecimal newBalance = account.getCurrentBalance().subtract(amount);

        validateBalance(newBalance);

        updateCurrentBalance(account, newBalance);

        AccountActivity accountActivity = createAccountActivity(accountIdFrom, activityType, amount, newBalance);
        return accountActivity;
    }


    private Account getAccountById(Long accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        return accountOptional.orElseThrow(() -> {
            throw new AccountException(ErrorMessage.ACCOUNT_NOT_FOUND);
        });
    }

    private void validateBalance(BigDecimal remainingBalance) {
        if (remainingBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new MoneyException(ErrorMessage.INSUFFICIENT_BALANCE);
        }
    }

    private AccountActivity createAccountActivity(Long accountId, ActivityType activityType, BigDecimal amount, BigDecimal newBalance) {
        AccountActivity accountActivity = new AccountActivity();
        accountActivity.setAccountId(accountId);
        accountActivity.setActivityType(activityType);
        accountActivity.setAmount(amount);
        accountActivity.setCurrentBalance(newBalance);
        accountActivity.setTransactionDate(new Date());
        return accountActivityRepository.save(accountActivity);
    }

    private void updateCurrentBalance(Account account, BigDecimal newBalance) {
        account.setCurrentBalance(newBalance);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public AccountActivityDto deposit(MoneyActivityRequestDto moneyActivityRequestDto) {

        Long accountId = moneyActivityRequestDto.getAccountId();
        BigDecimal amount = moneyActivityRequestDto.getAmount();

        MoneyActivityDto moneyActivityDto = createMoneyActivityDto(accountId, amount, ActivityType.DEPOSIT);

        AccountActivity accountActivity = moneyIn(moneyActivityDto);

        AccountActivityDto accountActivityDto = AccountActivityMapper.fromAccountActivity(accountActivity);
        return accountActivityDto;
    }

    /*
     * bu method moneytransfer serviceden çağrıldığında yeni bir transaction açacak fakat deposit'den çağrıldığında aynı bean içerisinde olduğu için açmayacak
     * */
    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public AccountActivity moneyIn(MoneyActivityDto moneyActivityDto) {

        Long accountIdTo = moneyActivityDto.getAccountId();
        BigDecimal amount = moneyActivityDto.getAmount();
        ActivityType activityType = moneyActivityDto.getActivityType();

        Account account = getAccountById(accountIdTo);

        BigDecimal newBalance = account.getCurrentBalance().add(amount);

        AccountActivity accountActivity = createAccountActivity(accountIdTo, activityType, amount, newBalance);

        updateCurrentBalance(account, newBalance);

        return accountActivity;
    }
}
