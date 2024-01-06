package com.melik.creditcard.service.creditcardactivity;

import com.melik.creditcard.service.common.DomainComponent;
import com.melik.creditcard.service.common.exception.CreditCardDomainBusinessException;
import com.melik.creditcard.service.common.usecase.UseCaseHandler;
import com.melik.creditcard.service.creditcard.entity.CreditCard;
import com.melik.creditcard.service.creditcard.port.CreditCardPort;
import com.melik.creditcard.service.creditcard.usecase.CreditCardRetrieve;
import com.melik.creditcard.service.creditcardactivity.entity.CreditCardActivity;
import com.melik.creditcard.service.creditcardactivity.port.CreditCardActivityPort;
import com.melik.creditcard.service.creditcardactivity.port.RestCallPort;
import com.melik.creditcard.service.creditcardactivity.usecase.CreditCardDetails;
import com.melik.creditcard.service.creditcardactivity.usecase.CustomerResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class CreditCardActivityDetailsUseCaseHandler implements UseCaseHandler<CreditCardDetails, CreditCardRetrieve> {

    private final RestCallPort restCallPort;
    private final CreditCardPort creditCardPort;
    private final CreditCardActivityPort creditCardActivityPort;

    @Override
    public CreditCardDetails handle(CreditCardRetrieve creditCardRetrieve) {
        CreditCard creditCard = getCreditCardById(creditCardRetrieve.getCreditCardId());

        LocalDate termEndDate = creditCard.getCutoffDate();

        LocalDate termStartDate = termEndDate.minusMonths(1);

        CreditCardDetails creditCardDetails = getCreditCardDetails(creditCardRetrieve);

        CustomerResponseDto customerResponseDto = fetchCustomerByRestCall(creditCard);

        List<CreditCardActivity> creditCardActivityList = getCreditCardActivityList(creditCardRetrieve, termEndDate, termStartDate);

        updateCreditCardDetails(creditCardDetails, customerResponseDto, creditCardActivityList);

        return creditCardDetails;
    }

    private CreditCard getCreditCardById(Long creditCardId) {
        Optional<CreditCard> creditCardOptional = creditCardPort.retrieve(getCreditCardRetrieve(creditCardId));

        return creditCardOptional.orElseThrow(() -> {
            log.error("Not Found Credit Card By id: {}",creditCardId);
            throw new CreditCardDomainBusinessException("Not Found Credit Card By id: "+creditCardId);
        });
    }

    private CreditCardRetrieve getCreditCardRetrieve(Long creditCardId) {
        return CreditCardRetrieve.builder().creditCardId(creditCardId).build();
    }

    private CreditCardDetails getCreditCardDetails(CreditCardRetrieve creditCardRetrieve) {
        CreditCard creditCard = creditCardPort.retrieve(creditCardRetrieve).get();

        return CreditCardDetails.builder()
                .cardNo(creditCard.getCardNo())
                .expireDate(creditCard.getExpireDate())
                .currentDebt(creditCard.getCurrentDebt())
                .minimumPaymentAmount(creditCard.getMinimumPaymentAmount())
                .cutoffDate(creditCard.getCutoffDate())
                .dueDate(creditCard.getDueDate())
                .build();
    }

    private List<CreditCardActivity> getCreditCardActivityList(CreditCardRetrieve creditCardRetrieve, LocalDate termEndDate, LocalDate termStartDate) {
        return creditCardActivityPort.retrieveAllByCreditCardIdAndTransactionDateBetween(creditCardRetrieve.getCreditCardId(), termStartDate, termEndDate);
    }

    private CustomerResponseDto fetchCustomerByRestCall(CreditCard creditCard) {
        return restCallPort.restCallToCustomerService(creditCard.getCustomerId());
    }

    private void updateCreditCardDetails(CreditCardDetails creditCardDetails, 
                                         CustomerResponseDto customerResponseDto, 
                                         List<CreditCardActivity> creditCardActivityList) {
        creditCardDetails.setFirstName(customerResponseDto.getFirstName());
        creditCardDetails.setLastName(customerResponseDto.getLastName());
        creditCardDetails.setCreditCardActivityList(creditCardActivityList);
    }
}
