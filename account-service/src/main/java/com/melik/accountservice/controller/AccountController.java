package com.melik.accountservice.controller;

import com.melik.accountservice.dto.*;
import com.melik.accountservice.service.AccountActivityService;
import com.melik.accountservice.service.AccountService;
import com.melik.accountservice.service.MoneyTransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/account")
public class AccountController {

    private final AccountService accountService;
    private final MoneyTransferService moneyTransferService;
    private final AccountActivityService accountActivityService;

    @GetMapping
    public ApiResponse findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        List<AccountDto> accountDtoList = accountService.findAll(pageOptional, sizeOptional);
        return ApiResponse.of(HttpStatus.OK, "Accounts Retrieved", Map.of("accountDtoList", accountDtoList));
    }

    @GetMapping("/{id}")
    public ApiResponse findById(@PathVariable Long id) {
        AccountDto accountDto = accountService.findById(id);
        return ApiResponse.of(HttpStatus.OK, "Account Retrieved", Map.of("accountDto", accountDto));
    }

    @PostMapping
    public ApiResponse save(@RequestBody @Valid AccountSaveDto accountSaveDto) {
        AccountDto accountDto = accountService.save(accountSaveDto);
        return ApiResponse.of(HttpStatus.CREATED, "Account Saved", Map.of("accountDto", accountDto));
    }

    @PatchMapping("/cancel/{accountId}")
    public ApiResponse cancel(@PathVariable Long accountId) {
        accountService.cancel(accountId);
        return ApiResponse.of(HttpStatus.OK, "Account Canceled");
    }

    @PostMapping("/money-transfer")
    public ApiResponse transferMoney(@RequestBody @Valid MoneyTransferSaveDto moneyTransferSaveDto) {
        MoneyTransferDto moneyTransferDto = moneyTransferService.transferMoney(moneyTransferSaveDto);
        return ApiResponse.of(HttpStatus.OK, "Money Transfer Successfully", Map.of("moneyTransferDto", moneyTransferDto));
    }

    @PostMapping("/withdraw")
    public ApiResponse withdraw(@RequestBody @Valid MoneyActivityRequestDto moneyActivityRequestDto) {
        AccountActivityDto accountActivityDto = accountActivityService.withdraw(moneyActivityRequestDto);
        return ApiResponse.of(HttpStatus.OK, "Withdraw Is Successfully", Map.of("accountActivityDto", accountActivityDto));
    }

    @PostMapping("/deposit")
    public ApiResponse deposit(@RequestBody @Valid MoneyActivityRequestDto moneyActivityRequestDto) {
        AccountActivityDto accountActivityDto = accountActivityService.deposit(moneyActivityRequestDto);
        return ApiResponse.of(HttpStatus.OK, "Deposit Is Successfully", Map.of("accountActivityDto", accountActivityDto));
    }

}
