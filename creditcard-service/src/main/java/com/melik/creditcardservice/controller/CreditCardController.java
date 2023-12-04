package com.melik.creditcardservice.controller;

import com.melik.creditcardservice.dto.*;
import com.melik.creditcardservice.service.CreditCardActivityService;
import com.melik.creditcardservice.service.CreditCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
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
    public ResponseEntity findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        List<CreditCardDto> creditCardDtoList = creditCardService.findAll(pageOptional, sizeOptional);
        return ResponseEntity.ok(creditCardDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        CreditCardDto creditCardDto = creditCardService.findById(id);
        return ResponseEntity.ok(creditCardDto);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid CreditCardSaveDto creditCardSaveDto) {
        CreditCardDto creditCardDto = creditCardService.save(creditCardSaveDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creditCardDto);
    }

    @PatchMapping("/cancel/{cardId}")
    public ResponseEntity cancel(@PathVariable Long cardId) {
        creditCardService.cancel(cardId);
        return ResponseEntity.ok("Credit Card Cancelled");
    }

    @PostMapping("/spend")
    public ResponseEntity spend(@RequestBody @Valid CreditCardSpendDto creditCardSpendDto) {
        CreditCardActivityDto creditCardActivityDto = creditCardActivityService.spend(creditCardSpendDto);
        return ResponseEntity.ok(creditCardActivityDto);
    }

    @PostMapping("/refund/{activityId}")
    public ResponseEntity refund(@PathVariable Long activityId) {
        CreditCardActivityDto creditCardActivityDto = creditCardActivityService.refund(activityId);
        return ResponseEntity.ok(creditCardActivityDto);
    }

    @PostMapping("/payment")
    public ResponseEntity payment(@RequestBody @Valid CreditCardPaymentDto creditCardPaymentDto) {
        CreditCardActivityDto creditCardActivityDto = creditCardActivityService.payment(creditCardPaymentDto);
        return ResponseEntity.ok(creditCardActivityDto);
    }

    @GetMapping("/activities/{cardId}")
    public ResponseEntity findAllActivities(
            @PathVariable Long cardId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {

        List<CreditCardActivityDto> creditCardActivityDtoList = creditCardActivityService.findAllActivities(cardId, startDate, endDate, pageOptional, sizeOptional);
        return ResponseEntity.ok(creditCardActivityDtoList);
    }

    @GetMapping("/{cardId}/statements")
    public ResponseEntity statement(@PathVariable Long cardId) {
        CreditCardDetails creditCardDetails = creditCardActivityService.statement(cardId);
        return ResponseEntity.ok(creditCardDetails);
    }
}
