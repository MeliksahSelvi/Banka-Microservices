package com.melik.creditcard.service.adapters.creditcardactivity.jpa;

import com.melik.creditcard.service.adapters.creditcardactivity.jpa.entity.CreditCardActivityEntity;
import com.melik.creditcard.service.adapters.creditcardactivity.jpa.repository.CreditCardActivityRepository;
import com.melik.creditcard.service.creditcardactivity.entity.CreditCardActivity;
import com.melik.creditcard.service.creditcardactivity.port.CreditCardActivityPort;
import com.melik.creditcard.service.creditcardactivity.usecase.CreditCardActivityRetrieve;
import com.melik.creditcard.service.creditcardactivity.usecase.CreditCardActivityRetrieveAll;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.melik.creditcard.service.common.util.Constants.DEFAULT_PAGE;
import static com.melik.creditcard.service.common.util.Constants.DEFAULT_SIZE;

/**
 * @Author mselvi
 * @Created 04.01.2024
 */

@Service
@RequiredArgsConstructor
public class CreditCardActivityDataAdapter implements CreditCardActivityPort {

    private final CreditCardActivityRepository creditCardActivityRepository;

    @Override
    public List<CreditCardActivity> retrieveAll(CreditCardActivityRetrieveAll creditCardActivityRetrieveAll) {
        List<CreditCardActivityEntity> creditCardActivityList = getActivities(creditCardActivityRetrieveAll);
        return creditCardActivityList.stream().map(CreditCardActivityEntity::toModel).toList();
    }

    private List<CreditCardActivityEntity> getActivities(CreditCardActivityRetrieveAll retrieveAll) {
        PageRequest pageRequest = getPageRequest(retrieveAll.getPageOptional(), retrieveAll.getSizeOptional());

        Page<CreditCardActivityEntity> pageList = creditCardActivityRepository
                .findAllByCreditCardIdAndTransactionDateBetween(retrieveAll.getCardId(), retrieveAll.getStartDate(), retrieveAll.getEndDate(), pageRequest);

        return pageList.toList();
    }

    private PageRequest getPageRequest(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        return PageRequest.of(pageOptional.orElse(DEFAULT_PAGE), sizeOptional.orElse(DEFAULT_SIZE));
    }

    @Override
    public CreditCardActivity save(CreditCardActivity creditCardActivity) {
        var creditCardActivityEntity = new CreditCardActivityEntity();
        BeanUtils.copyProperties(creditCardActivity, creditCardActivityEntity);
        return creditCardActivityRepository.save(creditCardActivityEntity).toModel();
    }

    @Override
    public Optional<CreditCardActivity> retrieve(CreditCardActivityRetrieve creditCardActivityRetrieve) {
        return creditCardActivityRepository.findById(creditCardActivityRetrieve.getActivityId()).map(CreditCardActivityEntity::toModel);
    }

    @Override
    public List<CreditCardActivity> retrieveAllByCreditCardIdAndTransactionDateBetween(Long cardId, LocalDate termStartDate, LocalDate termEndDate) {
        return creditCardActivityRepository.
                findAllByCreditCardIdAndTransactionDateBetween(
                        cardId,
                        termStartDate.atStartOfDay(),
                        termEndDate.atStartOfDay()).stream()
                .map(CreditCardActivityEntity::toModel)
                .toList();
    }
}
