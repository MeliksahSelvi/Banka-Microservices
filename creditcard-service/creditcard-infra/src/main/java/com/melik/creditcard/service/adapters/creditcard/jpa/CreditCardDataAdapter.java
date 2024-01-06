package com.melik.creditcard.service.adapters.creditcard.jpa;

import com.melik.creditcard.service.adapters.creditcard.jpa.entity.CreditCardEntity;
import com.melik.creditcard.service.adapters.creditcard.jpa.repository.CreditCardRepository;
import com.melik.creditcard.service.common.exception.CreditCardDomainBusinessException;
import com.melik.creditcard.service.common.valueobject.Money;
import com.melik.creditcard.service.common.valueobject.StatusType;
import com.melik.creditcard.service.creditcard.entity.CreditCard;
import com.melik.creditcard.service.creditcard.port.CreditCardPort;
import com.melik.creditcard.service.creditcard.usecase.CreditCardRetrieve;
import com.melik.creditcard.service.creditcard.usecase.CreditCardRetrieveAll;
import com.melik.creditcard.service.creditcard.usecase.CreditCardSave;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.melik.creditcard.service.common.util.Constants.DEFAULT_PAGE;
import static com.melik.creditcard.service.common.util.Constants.DEFAULT_SIZE;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class CreditCardDataAdapter implements CreditCardPort {

    private final CreditCardRepository creditCardRepository;

    @Override
    public List<CreditCard> retrieveAll(CreditCardRetrieveAll creditCardRetrieveAll) {
        PageRequest pageRequest = getPageRequest(creditCardRetrieveAll);
        Page<CreditCardEntity> allCreditCard = creditCardRepository.findAll(pageRequest);
        return allCreditCard.toList().stream().map(CreditCardEntity::toModel).toList();
    }

    @Override
    public Optional<CreditCard> retrieve(CreditCardRetrieve creditCardRetrieve) {
        Optional<CreditCardEntity> creditCardEntity = creditCardRepository.findById(creditCardRetrieve.getCreditCardId());
        if (creditCardEntity.isEmpty()) {
            log.error("Credit Card Not Found By id: {}", creditCardRetrieve.getCreditCardId());
            throw new CreditCardDomainBusinessException("Credit Card Not Found By id: " + creditCardRetrieve.getCreditCardId());
        }

        return creditCardEntity.map(CreditCardEntity::toModel);
    }

    @Override
    public CreditCard persist(CreditCardSave creditCardSave, Money limit, LocalDate cutoffDate, LocalDate dueDate) {

        var creditCardEntity = createCreditCard(creditCardSave.getCustomerId(), limit, cutoffDate, dueDate);
        return creditCardEntity.toModel();
    }

    @Override
    public CreditCard update(CreditCard creditCard) {
        var creditCardEntity = new CreditCardEntity();
        BeanUtils.copyProperties(creditCard, creditCardEntity);
        creditCardEntity.setUpdatedAt(LocalDateTime.now());
        return creditCardRepository.save(creditCardEntity).toModel();
    }

    @Override
    public Optional<CreditCard> retrieveByCardNoAndCvvNoAndExpireDateAndStatusType(Long cardNo, Long cvvNo, LocalDate expireDate, StatusType active) {
        return creditCardRepository.findByCardNoAndCvvNoAndExpireDateAndStatusType(cardNo, cvvNo, expireDate, active)
                .map(CreditCardEntity::toModel);
    }

    private PageRequest getPageRequest(CreditCardRetrieveAll creditCardRetrieveAll) {
        return PageRequest.of(creditCardRetrieveAll.getPageOptional().orElse(DEFAULT_PAGE), creditCardRetrieveAll.getSizeOptional().orElse(DEFAULT_SIZE));
    }

    private CreditCardEntity createCreditCard(Long customerId, Money limit, LocalDate cutoffDate, LocalDate dueDate) {
        LocalDate expireDate = getExpireDate();
        Long cardNo = getRandomNumber(16);
        Long cvvNo = getRandomNumber(3);

        var creditCardEntity = new CreditCardEntity();
        creditCardEntity.setCustomerId(customerId);
        creditCardEntity.setCardNo(cardNo);
        creditCardEntity.setCvvNo(cvvNo);
        creditCardEntity.setDueDate(dueDate);
        creditCardEntity.setCutoffDate(cutoffDate);
        creditCardEntity.setExpireDate(expireDate);
        creditCardEntity.setTotalLimit(limit.getAmount());
        creditCardEntity.setAvailableCardLimit(limit.getAmount());
        creditCardEntity.setMinimumPaymentAmount(BigDecimal.ZERO);
        creditCardEntity.setCurrentDebt(BigDecimal.ZERO);
        creditCardEntity.setStatusType(StatusType.ACTIVE);
        return creditCardRepository.save(creditCardEntity);
    }

    private LocalDate getExpireDate() {
        return LocalDate.now().plusYears(3);
    }

    private Long getRandomNumber(int charCount) {
        String randomNumeric = RandomStringUtils.randomNumeric(charCount);

        Long randomLong = null;
        if (StringUtils.hasText(randomNumeric)) {
            randomLong = Long.valueOf(randomNumeric);
        }
        return randomLong;
    }
}
