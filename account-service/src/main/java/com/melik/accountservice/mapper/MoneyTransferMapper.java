package com.melik.accountservice.mapper;

import com.melik.accountservice.domain.MoneyTransfer;
import com.melik.accountservice.dto.MoneyTransferDto;
import com.melik.accountservice.dto.MoneyTransferSaveDto;
import org.springframework.beans.BeanUtils;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

public class MoneyTransferMapper {

    public static MoneyTransfer fromSaveDto(MoneyTransferSaveDto moneyTransferSaveDto) {
        MoneyTransfer moneyTransfer = new MoneyTransfer();
        BeanUtils.copyProperties(moneyTransferSaveDto, moneyTransfer);
        return moneyTransfer;
    }

    public static MoneyTransferDto fromMoneyTransfer(MoneyTransfer moneyTransfer) {
        MoneyTransferDto moneyTransferDto = new MoneyTransferDto();
        BeanUtils.copyProperties(moneyTransfer, moneyTransferDto);
        return moneyTransferDto;
    }
}
