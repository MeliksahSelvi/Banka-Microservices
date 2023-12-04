package com.melik.creditcardservice.service.impl;

import com.melik.common.module.exception.NotFoundException;
import com.melik.creditcardservice.domain.CreditCard;
import com.melik.creditcardservice.dto.CreditCardDto;
import com.melik.creditcardservice.dto.CreditCardSaveDto;
import com.melik.creditcardservice.enums.ErrorMessage;
import com.melik.creditcardservice.enums.StatusType;
import com.melik.creditcardservice.mapper.CreditCardMapper;
import com.melik.creditcardservice.repository.CreditCardRepository;
import com.melik.creditcardservice.service.CreditCardService;
import com.melik.creditcardservice.util.DateUtil;
import com.melik.creditcardservice.util.NumberUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.melik.common.module.util.Constants.DEFAULT_PAGE;
import static com.melik.common.module.util.Constants.DEFAULT_SIZE;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final CreditCardMapper creditCardMapper;

    @Override
    public List<CreditCardDto> findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        PageRequest pageRequest = getPageRequest(pageOptional, sizeOptional);
        List<CreditCard> creditCardList = creditCardRepository.findAllByStatusType(StatusType.ACTIVE, pageRequest).toList();

        List<CreditCardDto> creditCardDtoList = convertListToDtoList(creditCardList);
        return creditCardDtoList;
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

    private List<CreditCardDto> convertListToDtoList(List<CreditCard> creditCardList) {
        return creditCardList.stream().map(creditCardMapper::fromCreditCard).toList();
    }

    @Override
    public CreditCardDto findById(Long id) {
        CreditCard creditCard = getCreditCardById(id);
        CreditCardDto creditCardDto = creditCardMapper.fromCreditCard(creditCard);
        return creditCardDto;
    }

    private CreditCard getCreditCardById(Long creditCardId) {
        Optional<CreditCard> creditCardOptional = creditCardRepository.findById(creditCardId);

        return creditCardOptional.orElseThrow(() -> {
            throw new NotFoundException(ErrorMessage.CREDIT_CARD_NOT_FOUND.getMessage());
        });
    }

    @Override
    public CreditCardDto save(CreditCardSaveDto creditCardSaveDto) {
        BigDecimal earning = creditCardSaveDto.getEarning();
        String cutoffDayStr = creditCardSaveDto.getCutoffDay();
        Long customerId = creditCardSaveDto.getCustomerId();

        BigDecimal limit = calculateLimit(earning);

        LocalDate cutoffDateLocal = getCutoffDateLocal(cutoffDayStr);

        Date cutoffDate = DateUtil.convertToDate(cutoffDateLocal);
        Date dueDate = getDueDate(cutoffDateLocal);

        CreditCard creditCard = createCreditCard(customerId, limit, cutoffDate, dueDate);

        CreditCardDto creditCardDto = creditCardMapper.fromCreditCard(creditCard);
        return creditCardDto;
    }

    private BigDecimal calculateLimit(BigDecimal earning) {
        BigDecimal multiply = earning.multiply(BigDecimal.valueOf(3));
        return multiply;
    }

    private LocalDate getCutoffDateLocal(String cutoffDayStr) {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        Month nextMonth = Month.of(currentMonth).plus(1);

        Integer cutoffDay = getCutoffDay(cutoffDayStr);
        LocalDate cutoffDateLocal = LocalDate.of(currentYear, nextMonth, cutoffDay);
        return cutoffDateLocal;
    }

    private Integer getCutoffDay(String cutoffDayStr) {
        if (!StringUtils.hasText(cutoffDayStr)) {
            cutoffDayStr = "1";
        }

        Integer cutoffDay = Integer.valueOf(cutoffDayStr);
        return cutoffDay;
    }

    private Date getDueDate(LocalDate cutoffDateLocal) {
        LocalDate dueDateLocal = getDueDateLocal(cutoffDateLocal);
        Date dueDate = DateUtil.convertToDate(dueDateLocal);
        return dueDate;
    }

    private LocalDate getDueDateLocal(LocalDate cutoffDateLocal) {
        LocalDate localDate = cutoffDateLocal.plusDays(10);
        return localDate;
    }

    private CreditCard createCreditCard(Long customerId, BigDecimal limit, Date cutoffDate, Date dueDate) {
        Date expireDate = getExpireDate();
        Long cardNo = getCardNo();
        Long cvvNo = getCvvNo();

        CreditCard creditCard = new CreditCard();
        creditCard.setCustomerId(customerId);
        creditCard.setCardNo(cardNo);
        creditCard.setCvvNo(cvvNo);
        creditCard.setDueDate(dueDate);
        creditCard.setCutoffDate(cutoffDate);
        creditCard.setExpireDate(expireDate);
        creditCard.setTotalLimit(limit);
        creditCard.setAvailableCardLimit(limit);
        creditCard.setMinimumPaymentAmount(BigDecimal.ZERO);
        creditCard.setCurrentDebt(BigDecimal.ZERO);
        creditCard.setStatusType(StatusType.ACTIVE);

        creditCard = creditCardRepository.save(creditCard);
        return creditCard;
    }

    private Date getExpireDate() {
        LocalDate expireDateLocal = getExpireDateLocal();
        Date expireDate = DateUtil.convertToDate(expireDateLocal);
        return expireDate;
    }

    private LocalDate getExpireDateLocal() {
        return LocalDate.now().plusYears(3);
    }

    private Long getCardNo() {
        Long cardNo = NumberUtil.getRandomNumber(16);
        return cardNo;
    }

    private Long getCvvNo() {
        Long cvvNo = NumberUtil.getRandomNumber(3);
        return cvvNo;
    }

    @Override
    @Transactional
    public void cancel(Long cardId) {
        CreditCard creditCard = getCreditCardById(cardId);
        creditCard.setStatusType(StatusType.PASSIVE);
        creditCard.setCancelDate(new Date());
        creditCardRepository.save(creditCard);
    }
}
