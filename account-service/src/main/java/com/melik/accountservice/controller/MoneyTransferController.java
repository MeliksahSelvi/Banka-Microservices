package com.melik.accountservice.controller;

import com.melik.accountservice.dto.MoneyTransferDto;
import com.melik.accountservice.dto.MoneyTransferSaveDto;
import com.melik.accountservice.service.MoneyTransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author mselvi
 * @Created 04.12.2023
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/moneytransfer", produces = "application/vnd.api.v1+json")
public class MoneyTransferController {

    private final MoneyTransferService moneyTransferService;

    @PostMapping
    public ResponseEntity transferMoney(@RequestBody @Valid MoneyTransferSaveDto moneyTransferSaveDto) {
        MoneyTransferDto moneyTransferDto = moneyTransferService.transferMoney(moneyTransferSaveDto);
        return ResponseEntity.ok(moneyTransferDto);
    }
}
