package com.melik.account.service.adapters.accountactivity.rest;

import com.melik.account.service.accountactivity.entity.AccountActivity;
import com.melik.account.service.accountactivity.usecase.MoneyActivity;
import com.melik.account.service.adapters.accountactivity.rest.dto.AccountActivityDto;
import com.melik.account.service.adapters.accountactivity.rest.dto.MoneyActivityRequestDto;
import com.melik.account.service.common.usecase.UseCaseHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping(value = "/api/v1/account/activity")
public class AccountActivityController {

    @Qualifier("moneyActivityWithdrawUseCaseHandler")
    private final UseCaseHandler<AccountActivity, MoneyActivity> moneyActivityWithdrawUseCaseHandler;

    @Qualifier("moneyActivityDepositUseCaseHandler")
    private final UseCaseHandler<AccountActivity, MoneyActivity> moneyActivityDepositUseCaseHandler;

    @PostMapping("/withdraw")
    public ResponseEntity<AccountActivityDto> withdraw(@RequestBody @Valid MoneyActivityRequestDto moneyActivityRequestDto) {
        var accountActivity = moneyActivityWithdrawUseCaseHandler.handle(moneyActivityRequestDto.toModel());
        return ResponseEntity.ok(AccountActivityDto.fromModel(accountActivity));
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountActivityDto> deposit(@RequestBody @Valid MoneyActivityRequestDto moneyActivityRequestDto) {
        var accountActivity = moneyActivityDepositUseCaseHandler.handle(moneyActivityRequestDto.toModel());
        return ResponseEntity.ok(AccountActivityDto.fromModel(accountActivity));
    }
}
