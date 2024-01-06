package com.melik.account.service.adapters.moneytransfer.jpa;

import com.melik.account.service.adapters.moneytransfer.jpa.entity.MoneyTransferEntity;
import com.melik.account.service.adapters.moneytransfer.jpa.repository.MoneyTransferRepository;
import com.melik.account.service.moneytransfer.entity.MoneyTransfer;
import com.melik.account.service.moneytransfer.port.MoneyTransferPort;
import com.melik.account.service.moneytransfer.usecase.MoneyTransferCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Service
@RequiredArgsConstructor
public class MoneyTransferDataAdapter implements MoneyTransferPort {

    private final MoneyTransferRepository moneyTransferRepository;

    @Override
    public MoneyTransfer persist(MoneyTransferCase moneyTransferCase) {
        var moneyTransfer = new MoneyTransferEntity();
        moneyTransfer.setAccountIdFrom(moneyTransferCase.getAccountIdFrom());
        moneyTransfer.setAccountIdTo(moneyTransferCase.getAccountIdTo());
        moneyTransfer.setAmount(moneyTransferCase.getAmount().getAmount());
        moneyTransfer.setDescription(moneyTransferCase.getDescription());
        moneyTransfer.setTransferType(moneyTransferCase.getTransferType());
        moneyTransfer.setTransferDate(LocalDateTime.now());
        return moneyTransferRepository.save(moneyTransfer).toModel();
    }
}
