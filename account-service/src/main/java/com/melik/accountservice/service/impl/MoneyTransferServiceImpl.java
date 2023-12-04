package com.melik.accountservice.service.impl;

import com.melik.accountservice.domain.MoneyTransfer;
import com.melik.accountservice.dto.MoneyActivityDto;
import com.melik.accountservice.dto.MoneyTransferDto;
import com.melik.accountservice.dto.MoneyTransferSaveDto;
import com.melik.accountservice.enums.ActivityType;
import com.melik.accountservice.mapper.MoneyTransferMapper;
import com.melik.accountservice.repository.MoneyTransferRepository;
import com.melik.accountservice.service.AccountActivityService;
import com.melik.accountservice.service.MoneyTransferService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Service
@RequiredArgsConstructor
public class MoneyTransferServiceImpl implements MoneyTransferService {

    private final MoneyTransferRepository moneyTransferRepository;
    private final AccountActivityService accountActivityService;
    private final MoneyTransferMapper moneyTransferMapper;

    /*
     * jakarta transaction seçtik çünkü spring transaction sadece bean'ler üzerinde kanal açıyor.
     * */
    @Override
    @Transactional
    public MoneyTransferDto transferMoney(MoneyTransferSaveDto moneyTransferSaveDto) {
        MoneyTransfer moneyTransfer = moneyTransferMapper.fromSaveDto(moneyTransferSaveDto);

        Long accountIdFrom = moneyTransfer.getAccountIdFrom();
        Long accountIdTo = moneyTransfer.getAccountIdTo();
        BigDecimal amount = moneyTransfer.getAmount();

        MoneyActivityDto moneyActivityDtoOut = createMoneyActivityDto(accountIdFrom, amount, ActivityType.SEND);

        accountActivityService.moneyOut(moneyActivityDtoOut);

        MoneyActivityDto moneyActivityDtoIn = createMoneyActivityDto(accountIdTo, amount, ActivityType.GET);

        accountActivityService.moneyIn(moneyActivityDtoIn);

        moneyTransfer.setTransferDate(new Date());
        moneyTransfer = moneyTransferRepository.save(moneyTransfer);

        MoneyTransferDto moneyTransferDto = moneyTransferMapper.fromMoneyTransfer(moneyTransfer);

        return moneyTransferDto;
    }

    private MoneyActivityDto createMoneyActivityDto(Long accountId, BigDecimal amount, ActivityType activityType) {
        MoneyActivityDto moneyActivityDto = new MoneyActivityDto();
        moneyActivityDto.setAccountId(accountId);
        moneyActivityDto.setAmount(amount);
        moneyActivityDto.setActivityType(activityType);
        return moneyActivityDto;
    }
}
