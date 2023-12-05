package com.melik.creditcardservice.controller;

import com.melik.creditcardservice.dto.CreditCardActivityDto;
import com.melik.creditcardservice.dto.CreditCardDetails;
import com.melik.creditcardservice.dto.CreditCardPaymentDto;
import com.melik.creditcardservice.dto.CreditCardSpendDto;
import com.melik.creditcardservice.service.CreditCardActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 04.12.2023
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/activity", produces = "application/vnd.api.v1+json")
public class CreditCardActivityController {

    private final CreditCardActivityService creditCardActivityService;

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
