package com.melik.creditcard.service.adapters.creditcardactivity.rest;

import com.melik.creditcard.service.adapters.creditcardactivity.rest.dto.CreditCardActivityRequestDto;
import com.melik.creditcard.service.adapters.creditcardactivity.rest.dto.CreditCardActivityResponseDto;
import com.melik.creditcard.service.adapters.creditcardactivity.rest.dto.CreditCardActivitySpendDto;
import com.melik.creditcard.service.adapters.creditcardactivity.rest.dto.CreditCardPaymentDto;
import com.melik.creditcard.service.common.usecase.UseCaseHandler;
import com.melik.creditcard.service.creditcard.usecase.CreditCardRetrieve;
import com.melik.creditcard.service.creditcardactivity.entity.CreditCardActivity;
import com.melik.creditcard.service.creditcardactivity.usecase.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/activity")
public class CreditCardActivityController {

    private final UseCaseHandler<List<CreditCardActivity>, CreditCardActivityRetrieveAll> creditCardActivityRetrieveAllUseCaseHandler;
    private final UseCaseHandler<CreditCardDetails, CreditCardRetrieve> creditCardActivityDetailsUseCaseHandler;
    private final UseCaseHandler<CreditCardActivity, CreditCardActivitySpend> creditCardActivitySpendUseCaseHandler;
    private final UseCaseHandler<CreditCardActivity, CreditCardActivityRetrieve> creditCardActivityRefundUseCaseHandler;
    private final UseCaseHandler<CreditCardActivity, CreditCardActivityPayment> creditCardActivityPaymentUseCaseHandler;

    @GetMapping("/activities/{cardId}")
    public ResponseEntity findAllActivities(@RequestBody @Valid CreditCardActivityRequestDto creditCardActivityRequestDto) {
        var creditCardActivityList = creditCardActivityRetrieveAllUseCaseHandler.handle(creditCardActivityRequestDto.toModel());
        return ResponseEntity.ok(creditCardActivityList.stream().map(CreditCardActivityResponseDto::fromModel).toList());
    }

    @GetMapping("/statements/{cardId}")
    public ResponseEntity statement(@PathVariable Long cardId) {
        var creditCardDetails = creditCardActivityDetailsUseCaseHandler.handle(toCardUseCase(cardId));
        return ResponseEntity.ok(creditCardDetails);
    }

    @PostMapping("/spend")
    public ResponseEntity<CreditCardActivityResponseDto> spend(@RequestBody @Valid CreditCardActivitySpendDto creditCardActivitySpendDto) {
        var creditCardActivity = creditCardActivitySpendUseCaseHandler.handle(creditCardActivitySpendDto.toModel());
        return ResponseEntity.ok(CreditCardActivityResponseDto.fromModel(creditCardActivity));
    }

    @PostMapping("/refund/{activityId}")
    public ResponseEntity<CreditCardActivityResponseDto> refund(@PathVariable Long activityId) {
        var creditCardActivity = creditCardActivityRefundUseCaseHandler.handle(toUseCase(activityId));
        return ResponseEntity.ok(CreditCardActivityResponseDto.fromModel(creditCardActivity));
    }

    @PostMapping("/payment")
    public ResponseEntity<CreditCardActivityResponseDto> payment(@RequestBody @Valid CreditCardPaymentDto creditCardPaymentDto) {
        var creditCardActivity = creditCardActivityPaymentUseCaseHandler.handle(creditCardPaymentDto.toModel());
        return ResponseEntity.ok(CreditCardActivityResponseDto.fromModel(creditCardActivity));
    }

    private CreditCardActivityRetrieve toUseCase(Long activityId) {
        return CreditCardActivityRetrieve.builder().activityId(activityId).build();
    }

    private CreditCardRetrieve toCardUseCase(Long cardId) {
        return CreditCardRetrieve.builder().creditCardId(cardId).build();
    }
}
