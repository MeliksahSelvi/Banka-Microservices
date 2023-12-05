package com.melik.creditcardservice.service.impl;

import com.melik.common.module.exception.BankException;
import com.melik.common.module.exception.NotFoundException;
import com.melik.creditcardservice.config.InnerRestCallData;
import com.melik.creditcardservice.domain.CreditCard;
import com.melik.creditcardservice.domain.CreditCardActivity;
import com.melik.creditcardservice.dto.*;
import com.melik.creditcardservice.enums.CardActivityType;
import com.melik.creditcardservice.enums.ErrorMessage;
import com.melik.creditcardservice.enums.StatusType;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
    private final InnerRestCallData innerRestCallData;
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
            throw new BankException(ErrorMessage.INVALID_CARD.getMessage());
        });

        if (creditCard.getExpireDate().before(new Date())) {
            throw new BankException(ErrorMessage.CREDIT_CARD_EXPIRED.getMessage());
        }
    }

    private void validatedCardLimit(BigDecimal currentAvailableLimit) {
        if (currentAvailableLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new BankException(ErrorMessage.INSUFFICIENT_CREDIT_CARD_LIMIT.getMessage());
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
            throw new NotFoundException(ErrorMessage.ACTIVITY_NOT_FOUND.getMessage());
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
            throw new NotFoundException(ErrorMessage.CREDIT_CARD_NOT_FOUND.getMessage());
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
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJpZFwiOjEsXCJlbWFpbFwiOlwibWVsaWtzYWguc2VsdmkyODM0QGdtYWlsLmNvbVwiLFwicGFzc3dvcmRcIjpcIiQyYSQxMCREY1l5aEdmMUkyQ1NSdWFSMTBQR1BPNml0TDF4cnRXOS8xTHZ1QzJyNU80d3RDcFJGUnRwNlwiLFwiYXV0aG9yaXRpZXNcIjpbe1wicm9sZVwiOlwiY3VzdG9tZXJcIn1dfSIsImlhdCI6MTcwMTc3MjM1MSwiZXhwIjoxNzAxODU4MzUxfQ.y2oIpTjJ7yYDFeFuisQOLvEiXWzch7DaZH6iHjzJMD2-suxI14sRWdNgRZ6LNC26rBM1EanrniAF3wIlHWT8sw";

        return webClientBuilder.build()
                .method(HttpMethod.valueOf(innerRestCallData.getMethod()))
                .uri(innerRestCallData.getUri(), uriBuilder -> uriBuilder.build(customerId))
                .headers(h -> h.setBearerAuth(token))
                .headers(h -> h.setContentType(MediaType.APPLICATION_JSON))
                .accept(MediaType.valueOf(innerRestCallData.getAccept()))
                .retrieve()
                .onStatus(
                        s -> s.equals(HttpStatus.UNAUTHORIZED),
                        clientResponse -> Mono.just(new BankException("Not Authenticated")))
                .onStatus(
                        s -> s.equals(HttpStatus.BAD_REQUEST),
                        clientResponse -> Mono.just(new BankException(clientResponse.statusCode().toString())))
                .onStatus(
                        s -> s.equals(HttpStatus.INTERNAL_SERVER_ERROR),
                        clientResponse -> Mono.just(new Exception(clientResponse.statusCode().toString())))
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
