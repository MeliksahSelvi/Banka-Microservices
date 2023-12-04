package com.melik.creditcardservice.service.impl;

import com.melik.creditcardservice.domain.CreditCard;
import com.melik.creditcardservice.domain.CreditCardActivity;
import com.melik.creditcardservice.dto.*;
import com.melik.creditcardservice.enums.CardActivityType;
import com.melik.creditcardservice.enums.ErrorMessage;
import com.melik.creditcardservice.enums.StatusType;
import com.melik.creditcardservice.exception.CreditCardActivityException;
import com.melik.creditcardservice.exception.CreditCardException;
import com.melik.creditcardservice.mapper.CreditCardActivityMapper;
import com.melik.creditcardservice.repository.CreditCardActivityRepository;
import com.melik.creditcardservice.repository.CreditCardRepository;
import com.melik.creditcardservice.service.CreditCardActivityService;
import com.melik.creditcardservice.util.DateUtil;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.melik.common.module.util.Constants.DEFAULT_PAGE;
import static com.melik.common.module.util.Constants.DEFAULT_SIZE;

/**
 * @Author mselvi
 * @Created 16.09.2023
 */

@Service
@RequiredArgsConstructor
public class CreditCardActivityServiceImpl implements CreditCardActivityService {

    private final CreditCardRepository creditCardRepository;
    private final CreditCardActivityRepository creditCardActivityRepository;
    private final CreditCardActivityMapper creditCardActivityMapper;
    private final WebClient.Builder webClientBuilder;

    @Override
    @Transactional
    public CreditCardActivityDto spend(CreditCardSpendDto creditCardSpendDto) {
        BigDecimal amount = creditCardSpendDto.getAmount();
        String description = creditCardSpendDto.getDescription();

        CreditCard creditCard = getCreditCard(creditCardSpendDto);

        BigDecimal currentDebt = creditCard.getCurrentDebt().add(amount);
        BigDecimal currentAvailableLimit = creditCard.getAvailableCardLimit().subtract(amount);

        validatedCardLimit(currentAvailableLimit);

        creditCard = updateCreditCardForSpend(creditCard, currentDebt, currentAvailableLimit);

        CreditCardActivity creditCardActivity = createCreditCardActivity(amount, description, creditCard.getId(), CardActivityType.SPEND);

        CreditCardActivityDto creditCardActivityDto = creditCardActivityMapper.fromCreditCardActivity(creditCardActivity);
        return creditCardActivityDto;
    }

    private CreditCard getCreditCard(CreditCardSpendDto creditCardSpendDto) {
        Long cardNo = creditCardSpendDto.getCardNo();
        Long cvvNo = creditCardSpendDto.getCvvNo();
        Date expireDate = creditCardSpendDto.getExpireDate();

        Optional<CreditCard> creditCardOptional = creditCardRepository.findByCardNoAndCvvNoAndExpireDateAndStatusType(cardNo, cvvNo, expireDate, StatusType.ACTIVE);

        validateCreditCard(creditCardOptional);

        return creditCardOptional.get();
    }

    private void validateCreditCard(Optional<CreditCard> creditCardOptional) {
        CreditCard creditCard = creditCardOptional.orElseThrow(() -> {
            throw new CreditCardException(ErrorMessage.INVALID_CARD);
        });

        if (creditCard.getExpireDate().before(new Date())) {
            throw new CreditCardException(ErrorMessage.CREDIT_CARD_EXPIRED);
        }
    }

    private void validatedCardLimit(BigDecimal currentAvailableLimit) {
        if (currentAvailableLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new CreditCardException(ErrorMessage.INSUFFICIENT_CREDIT_CARD_LIMIT);
        }
    }

    private CreditCard updateCreditCardForSpend(CreditCard creditCard, BigDecimal currentDebt, BigDecimal currentAvailableLimit) {
        creditCard.setAvailableCardLimit(currentAvailableLimit);
        creditCard.setCurrentDebt(currentDebt);

        return creditCardRepository.save(creditCard);
    }

    private CreditCardActivity createCreditCardActivity(BigDecimal amount, String description, Long creditCardId, CardActivityType cardActivityType) {
        CreditCardActivity creditCardActivity = new CreditCardActivity();
        creditCardActivity.setCreditCardId(creditCardId);
        creditCardActivity.setAmount(amount);
        creditCardActivity.setDescription(description);
        creditCardActivity.setTransactionDate(new Date());
        creditCardActivity.setCardActivityType(cardActivityType);

        creditCardActivity = creditCardActivityRepository.save(creditCardActivity);
        return creditCardActivity;
    }

    @Override
    @Transactional
    public CreditCardActivityDto refund(Long activityId) {
        CreditCardActivity oldCreditCardActivity = getCreditCardActivityById(activityId);
        BigDecimal amount = oldCreditCardActivity.getAmount();
        String description = "REFUND -> " + oldCreditCardActivity.getDescription();

        CreditCard creditCard = updateCreditCardForRefund(oldCreditCardActivity, amount);

        CreditCardActivity creditCardActivity = createCreditCardActivity(amount, description, creditCard.getId(), CardActivityType.REFUND);

        CreditCardActivityDto creditCardActivityDto = creditCardActivityMapper.fromCreditCardActivity(creditCardActivity);
        return creditCardActivityDto;
    }

    private CreditCardActivity getCreditCardActivityById(Long activityId) {
        Optional<CreditCardActivity> activityOptional = creditCardActivityRepository.findById(activityId);

        return activityOptional.orElseThrow(() -> {
            throw new CreditCardActivityException(ErrorMessage.ACTIVITY_NOT_FOUND);
        });
    }

    private CreditCard updateCreditCardForRefund(CreditCardActivity oldCreditCardActivity, BigDecimal amount) {
        CreditCard creditCard = getCreditCardById(oldCreditCardActivity.getCreditCardId());

        return switch (oldCreditCardActivity.getCardActivityType()) {
            case SPEND -> addLimitToCard(creditCard, amount);
            case PAYMENT -> substractLimitToCard(creditCard, amount);
            case REFUND -> creditCard;
        };
    }

    private CreditCard getCreditCardById(Long creditCardId) {
        Optional<CreditCard> creditCardOptional = creditCardRepository.findById(creditCardId);

        return creditCardOptional.orElseThrow(() -> {
            throw new CreditCardException(ErrorMessage.CREDIT_CARD_NOT_FOUND);
        });
    }

    private CreditCard addLimitToCard(CreditCard creditCard, BigDecimal amount) {
        BigDecimal currentDebt = creditCard.getCurrentDebt().subtract(amount);
        BigDecimal currentAvailableLimit = creditCard.getAvailableCardLimit().add(amount);

        creditCard.setCurrentDebt(currentDebt);
        creditCard.setAvailableCardLimit(currentAvailableLimit);
        return creditCardRepository.save(creditCard);
    }

    private CreditCard substractLimitToCard(CreditCard creditCard, BigDecimal amount) {
        BigDecimal currentDebt = creditCard.getCurrentDebt().add(amount);
        BigDecimal currentAvailableLimit = creditCard.getAvailableCardLimit().subtract(amount);

        creditCard.setCurrentDebt(currentDebt);
        creditCard.setAvailableCardLimit(currentAvailableLimit);
        return creditCardRepository.save(creditCard);
    }

    @Override
    @Transactional
    public CreditCardActivityDto payment(CreditCardPaymentDto creditCardPaymentDto) {
        Long creditCardId = creditCardPaymentDto.getCreditCardId();
        BigDecimal amount = creditCardPaymentDto.getAmount();
        String description = "Payment";
        updateCreditCardForPayment(creditCardId, amount);

        CreditCardActivity creditCardActivity = createCreditCardActivity(amount, description, creditCardId, CardActivityType.PAYMENT);

        CreditCardActivityDto creditCardActivityDto = creditCardActivityMapper.fromCreditCardActivity(creditCardActivity);
        return creditCardActivityDto;
    }

    private CreditCard updateCreditCardForPayment(Long creditCardId, BigDecimal amount) {
        CreditCard creditCard = getCreditCardById(creditCardId);

        creditCard = addLimitToCard(creditCard, amount);
        return creditCard;
    }

    @Override
    public List<CreditCardActivityDto> findAllActivities(Long cardId, Date startDate, Date endDate, Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {

        List<CreditCardActivity> creditCardActivityList = getActivities(cardId, startDate, endDate, pageOptional, sizeOptional);

        List<CreditCardActivityDto> creditCardActivityDtoList = convertActivityListToDtoList(creditCardActivityList);
        return creditCardActivityDtoList;
    }

    private List<CreditCardActivity> getActivities(Long cardId, Date startDate, Date endDate, Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {

        PageRequest pageRequest = getPageRequest(pageOptional, sizeOptional);

        Page<CreditCardActivity> pageList = creditCardActivityRepository
                .findAllByCreditCardIdAndTransactionDateBetween(cardId, startDate, endDate, pageRequest);

        return pageList.toList();
    }

    private PageRequest getPageRequest(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        Integer page = getPageOptionalValue(pageOptional);

        Integer size = getSizeOptionalValue(sizeOptional);

        PageRequest pageRequest = PageRequest.of(page, size);
        return pageRequest;
    }

    private Integer getPageOptionalValue(Optional<Integer> pageOptional) {
        return pageOptional.orElse(DEFAULT_PAGE);
    }

    private Integer getSizeOptionalValue(Optional<Integer> sizeOptional) {
        return sizeOptional.orElse(DEFAULT_SIZE);
    }

    private List<CreditCardActivityDto> convertActivityListToDtoList(List<CreditCardActivity> activityList) {
        return activityList.stream().map(creditCardActivityMapper::fromCreditCardActivity).toList();
    }

    @Override
    public CreditCardDetails statement(Long cardId) {
        CreditCard creditCard = getCreditCardById(cardId);

        Date termEndDate = creditCard.getCutoffDate();

        LocalDate cutoffDateLocal = DateUtil.convertToLocalDate(termEndDate);

        LocalDate termStartDateLocal = cutoffDateLocal.minusMonths(1);
        Date termStartDate = DateUtil.convertToDate(termStartDateLocal);

        CreditCardDetails creditCardDetails = creditCardRepository.getCreditCardDetails(cardId);

        updateCreditCardDetailsByCustomerInfo(creditCard.getCustomerId(), creditCardDetails);

        updateCreditCardDetailsByActivity(cardId, termEndDate, termStartDate, creditCardDetails);

        return creditCardDetails;
    }

    private void updateCreditCardDetailsByCustomerInfo(Long customerId, CreditCardDetails creditCardDetails) {
        CustomerDto customerDto = fetchResponseCustomerService(customerId);

        creditCardDetails.setFirstName(customerDto.getFirstName());
        creditCardDetails.setLastName(customerDto.getLastName());
    }

    @Retry(name = "customer")
    private CustomerDto fetchResponseCustomerService(Long customerId) {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZWxpa3NhaC5zZWx2aTI4MzRAZ21haWwuY29tIiwiaWF0IjoxNzAxNjk4MzE1LCJleHAiOjE3MDE3ODQzMTV9.ovXMoqANwBfAWIFQBVj9B58g_Vmnpp9-T7KGIudP0JLv2qD-eVNdvORcMf8dH-DzTysvQDJ3LAwYjTzT6PvKQQ";
        return webClientBuilder.build().get()
                .uri(uriBuilder -> uriBuilder.path("http://customer-service/api/v1/customer/{id}").build(customerId))
                .headers(h -> h.setBearerAuth(token))
                .headers(h->h.setContentType(MediaType.APPLICATION_JSON))
                .retrieve()
                .bodyToMono(CustomerDto.class)
                .block();
    }

    private void updateCreditCardDetailsByActivity(Long cardId, Date termEndDate, Date termStartDate, CreditCardDetails creditCardDetails) {
        List<CreditCardActivityDto> creditCardActivityDtoList = getCardActivityDtoList(cardId, termEndDate, termStartDate);

        creditCardDetails.setCreditCardActivityDtoList(creditCardActivityDtoList);
    }

    private List<CreditCardActivityDto> getCardActivityDtoList(Long cardId, Date termEndDate, Date termStartDate) {
        List<CreditCardActivity> creditCardActivityList = creditCardActivityRepository
                .findAllByCreditCardIdAndTransactionDateBetween(cardId, termStartDate, termEndDate);

        List<CreditCardActivityDto> creditCardActivityDtoList = convertActivityListToDtoList(creditCardActivityList);
        return creditCardActivityDtoList;
    }
}
