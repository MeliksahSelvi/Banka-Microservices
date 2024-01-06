package com.melik.account.service.adapters.account.rest;

import com.melik.account.service.account.entity.Account;
import com.melik.account.service.account.usecase.AccountRetrieve;
import com.melik.account.service.account.usecase.AccountRetrieveAll;
import com.melik.account.service.account.usecase.AccountSave;
import com.melik.account.service.adapters.account.rest.dto.AccountDto;
import com.melik.account.service.adapters.account.rest.dto.AccountSaveDto;
import com.melik.account.service.common.usecase.UseCaseHandler;
import com.melik.account.service.common.usecase.VoidUseCaseHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/accounts")
public class AccountController {

    private final UseCaseHandler<List<Account>, AccountRetrieveAll> accountRetrieveAllUseCaseHandler;
    private final UseCaseHandler<Account, AccountRetrieve> accountRetrieveUseCaseHandler;
    private final UseCaseHandler<Account, AccountSave> accountSaveUseCaseHandler;
    private final VoidUseCaseHandler<AccountRetrieve> accountCancelVoidUseCaseHandler;

    @GetMapping
    public ResponseEntity<List<AccountDto>> findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        var accountList = accountRetrieveAllUseCaseHandler.handle(toUseCase(pageOptional, sizeOptional));
        return ResponseEntity.ok(accountList.stream().map(AccountDto::fromModel).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> findById(@PathVariable Long id) {
        var account = accountRetrieveUseCaseHandler.handle(toUseCase(id));
        return ResponseEntity.ok(AccountDto.fromModel(account));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid AccountSaveDto accountSaveDto) {
        var account = accountSaveUseCaseHandler.handle(accountSaveDto.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountDto.fromModel(account));
    }

    @PatchMapping("/cancel/{accountId}")
    public ResponseEntity<String> cancel(@PathVariable Long accountId) {
        accountCancelVoidUseCaseHandler.handle(toUseCase(accountId));
        return ResponseEntity.ok("Account Cancelled");
    }

    private AccountRetrieveAll toUseCase(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        return AccountRetrieveAll.builder().pageOptional(pageOptional).sizeOptional(sizeOptional).build();
    }

    private AccountRetrieve toUseCase(Long id) {
        return AccountRetrieve.builder().accountId(id).build();
    }
}
