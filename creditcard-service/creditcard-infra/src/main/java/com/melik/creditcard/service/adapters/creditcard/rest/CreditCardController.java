package com.melik.creditcard.service.adapters.creditcard.rest;

import com.melik.creditcard.service.adapters.creditcard.rest.dto.CreditCardDto;
import com.melik.creditcard.service.adapters.creditcard.rest.dto.CreditCardSaveDto;
import com.melik.creditcard.service.common.usecase.UseCaseHandler;
import com.melik.creditcard.service.common.usecase.VoidUseCaseHandler;
import com.melik.creditcard.service.creditcard.entity.CreditCard;
import com.melik.creditcard.service.creditcard.usecase.CreditCardRetrieve;
import com.melik.creditcard.service.creditcard.usecase.CreditCardRetrieveAll;
import com.melik.creditcard.service.creditcard.usecase.CreditCardSave;
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
@RequestMapping(value = "/api/v1/cards")
public class CreditCardController {

    private final UseCaseHandler<List<CreditCard>, CreditCardRetrieveAll> creditCardRetrieveAllUseCaseHandler;
    private final UseCaseHandler<CreditCard, CreditCardRetrieve> creditCardRetrieveUseCaseHandler;
    private final UseCaseHandler<CreditCard, CreditCardSave> creditCardSaveUseCaseHandler;
    private final VoidUseCaseHandler<CreditCardRetrieve> creditCardCancelVoidUseCaseHandler;

    @GetMapping
    public ResponseEntity<List<CreditCardDto>> findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        var creditCardList = creditCardRetrieveAllUseCaseHandler.handle(toUseCase(pageOptional, sizeOptional));
        return ResponseEntity.ok(creditCardList.stream().map(CreditCardDto::fromModel).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCardDto> findById(@PathVariable Long id) {
        var creditCard = creditCardRetrieveUseCaseHandler.handle(toUseCase(id));
        return ResponseEntity.ok(CreditCardDto.fromModel(creditCard));
    }

    @PostMapping
    public ResponseEntity<CreditCardDto> save(@RequestBody @Valid CreditCardSaveDto creditCardSaveDto) {
        CreditCard creditCard = creditCardSaveUseCaseHandler.handle(creditCardSaveDto.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(CreditCardDto.fromModel(creditCard));
    }

    @PatchMapping("/cancel/{cardId}")
    public ResponseEntity<String > cancel(@PathVariable Long cardId) {
        creditCardCancelVoidUseCaseHandler.handle(toUseCase(cardId));
        return ResponseEntity.ok("Credit Card Cancelled");
    }

    private CreditCardRetrieveAll toUseCase(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        return CreditCardRetrieveAll.builder().pageOptional(pageOptional).sizeOptional(sizeOptional).build();
    }

    private CreditCardRetrieve toUseCase(Long creditCardId) {
        return CreditCardRetrieve.builder().creditCardId(creditCardId).build();
    }
}
