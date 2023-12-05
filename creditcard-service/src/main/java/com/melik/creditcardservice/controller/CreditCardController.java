package com.melik.creditcardservice.controller;

import com.melik.creditcardservice.dto.CreditCardDto;
import com.melik.creditcardservice.dto.CreditCardSaveDto;
import com.melik.creditcardservice.service.CreditCardService;
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
@RequestMapping(value = "/card",produces = "application/vnd.api.v1+json")
public class CreditCardController {

    private final CreditCardService creditCardService;

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
}
