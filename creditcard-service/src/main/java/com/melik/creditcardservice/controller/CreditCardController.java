package com.melik.creditcardservice.controller;

import com.melik.creditcardservice.dto.*;
import com.melik.creditcardservice.service.CreditCardActivityService;
import com.melik.creditcardservice.service.CreditCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/creditcard")
public class CreditCardController {

    private final CreditCardService creditCardService;
    private final CreditCardActivityService creditCardActivityService;

    @GetMapping
    public ApiResponse findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        List<CreditCardDto> creditCardDtoList = creditCardService.findAll(pageOptional, sizeOptional);
        return ApiResponse.of(HttpStatus.OK, "Credit Cards Retrieved", Map.of("creditCardDtoList", creditCardDtoList));
    }

    @GetMapping("/{id}")
    public ApiResponse findById(@PathVariable Long id) {
        CreditCardDto creditCardDto = creditCardService.findById(id);
        return ApiResponse.of(HttpStatus.OK, "Credit Card Retrieved", Map.of("creditCardDto", creditCardDto));
    }

    @PostMapping
    public ApiResponse save(@RequestBody @Valid CreditCardSaveDto creditCardSaveDto) {
        CreditCardDto creditCardDto = creditCardService.save(creditCardSaveDto);
        return ApiResponse.of(HttpStatus.CREATED, "Credit Card Is Created", Map.of("creditCardDto", creditCardDto));
    }

    @PatchMapping("/cancel/{cardId}")
    public ApiResponse cancel(@PathVariable Long cardId) {
        creditCardService.cancel(cardId);
        return ApiResponse.of(HttpStatus.OK, "Credit Card Canceled");
    }

    @PostMapping("/spend")
    public ApiResponse spend(@RequestBody @Valid CreditCardSpendDto creditCardSpendDto) {
        CreditCardActivityDto creditCardActivityDto = creditCardActivityService.spend(creditCardSpendDto);
        return ApiResponse.of(HttpStatus.OK, "Spend Action Is Successfully", Map.of("creditCardActivityDto", creditCardActivityDto));
    }

    @PostMapping("/refund/{activityId}")
    public ApiResponse refund(@PathVariable Long activityId) {
        CreditCardActivityDto creditCardActivityDto = creditCardActivityService.refund(activityId);
        return ApiResponse.of(HttpStatus.OK, "Refund Action Is Successfully", Map.of("creditCardActivityDto", creditCardActivityDto));
    }

    @PostMapping("/payment")
    public ApiResponse payment(@RequestBody @Valid CreditCardPaymentDto creditCardPaymentDto) {
        CreditCardActivityDto creditCardActivityDto = creditCardActivityService.payment(creditCardPaymentDto);
        return ApiResponse.of(HttpStatus.OK, "Payment Action Is Successfully", Map.of("creditCardActivityDto", creditCardActivityDto));
    }

    @GetMapping("/activities/{cardId}")
    public ApiResponse findAllActivities(
            @PathVariable Long cardId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {

        List<CreditCardActivityDto> creditCardActivityDtoList = creditCardActivityService.findAllActivities(cardId, startDate, endDate, pageOptional, sizeOptional);
        return ApiResponse.of(HttpStatus.OK, "Activities Retrieved", Map.of("creditCardActivityDtoList", creditCardActivityDtoList));
    }

    @GetMapping("/{cardId}/statements")
    public ApiResponse statement(@PathVariable Long cardId) {
        CreditCardDetails creditCardDetails = creditCardActivityService.statement(cardId);
        return ApiResponse.of(HttpStatus.OK, "Card Details Retrieved", Map.of("creditCardDetails", creditCardDetails));
    }
}
