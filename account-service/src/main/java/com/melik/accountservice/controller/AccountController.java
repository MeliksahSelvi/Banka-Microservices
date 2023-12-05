package com.melik.accountservice.controller;

import com.melik.accountservice.dto.AccountActivityDto;
import com.melik.accountservice.dto.AccountDto;
import com.melik.accountservice.dto.AccountSaveDto;
import com.melik.accountservice.dto.MoneyActivityRequestDto;
import com.melik.accountservice.service.AccountActivityService;
import com.melik.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/account", produces = "application/vnd.api.v1+json")
public class AccountController {

    private final AccountService accountService;
    private final AccountActivityService accountActivityService;


    @GetMapping
    public ResponseEntity findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        List<AccountDto> accountDtoList = accountService.findAll(pageOptional, sizeOptional);
        return ResponseEntity.ok(accountDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        AccountDto accountDto = accountService.findById(id);
        return ResponseEntity.ok(accountDto);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid AccountSaveDto accountSaveDto) {
        AccountDto accountDto = accountService.save(accountSaveDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountDto);
    }

    @PatchMapping("/cancel/{accountId}")
    public ResponseEntity cancel(@PathVariable Long accountId) {
        accountService.cancel(accountId);
        return ResponseEntity.ok("Account Cancelled");
    }

    @PostMapping("/withdraw")
    public ResponseEntity withdraw(@RequestBody @Valid MoneyActivityRequestDto moneyActivityRequestDto) {
        AccountActivityDto accountActivityDto = accountActivityService.withdraw(moneyActivityRequestDto);
        return ResponseEntity.ok(accountActivityDto);
    }

    @PostMapping("/deposit")
    public ResponseEntity deposit(@RequestBody @Valid MoneyActivityRequestDto moneyActivityRequestDto) {
        AccountActivityDto accountActivityDto = accountActivityService.deposit(moneyActivityRequestDto);
        return ResponseEntity.ok(accountActivityDto);
    }

}
