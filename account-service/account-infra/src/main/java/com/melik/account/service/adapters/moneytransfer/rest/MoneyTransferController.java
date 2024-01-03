package com.melik.account.service.adapters.moneytransfer.rest;

import com.melik.account.service.adapters.moneytransfer.rest.dto.MoneyTransferDto;
import com.melik.account.service.adapters.moneytransfer.rest.dto.MoneyTransferSaveDto;
import com.melik.account.service.common.usecase.UseCaseHandler;
import com.melik.account.service.moneytransfer.entity.MoneyTransfer;
import com.melik.account.service.moneytransfer.usecase.MoneyTransferCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/moneytransfer")
public class MoneyTransferController {

    private final UseCaseHandler<MoneyTransfer, MoneyTransferCase> moneyTransferCaseUseCaseHandler;

    @PostMapping
    public ResponseEntity transferMoney(@RequestBody @Valid MoneyTransferSaveDto moneyTransferSaveDto) {
        var moneyTransfer = moneyTransferCaseUseCaseHandler.handle(moneyTransferSaveDto.toModel());
        return ResponseEntity.ok(MoneyTransferDto.fromModel(moneyTransfer));
    }
}
