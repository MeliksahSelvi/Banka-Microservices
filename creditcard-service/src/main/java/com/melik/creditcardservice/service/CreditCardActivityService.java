package com.melik.creditcardservice.service;

import com.melik.creditcardservice.dto.CreditCardActivityDto;
import com.melik.creditcardservice.dto.CreditCardDetails;
import com.melik.creditcardservice.dto.CreditCardPaymentDto;
import com.melik.creditcardservice.dto.CreditCardSpendDto;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 16.09.2023
 */

public interface CreditCardActivityService {

    CreditCardActivityDto spend(CreditCardSpendDto creditCardSpendDto);

    CreditCardActivityDto refund(Long activityId);

    CreditCardActivityDto payment(CreditCardPaymentDto creditCardPaymentDto);

    List<CreditCardActivityDto> findAllActivities(Long cardId, Date startDate, Date endDate, Optional<Integer> pageOptional, Optional<Integer> sizeOptional);

    CreditCardDetails statement(Long cardId);
}
