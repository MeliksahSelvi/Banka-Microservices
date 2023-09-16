package com.melik.creditcardservice.service;

import com.melik.creditcardservice.dto.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 16.09.2023
 */

public interface CreditCardService {

    List<CreditCardDto> findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional);

    CreditCardDto findById(Long id);

    CreditCardDto save(CreditCardSaveDto creditCardSaveDto);

    void cancel(Long cardId);
}
