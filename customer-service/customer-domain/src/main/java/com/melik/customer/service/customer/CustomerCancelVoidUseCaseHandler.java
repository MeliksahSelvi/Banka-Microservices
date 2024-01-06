package com.melik.customer.service.customer;

import com.melik.customer.service.common.DomainComponent;
import com.melik.customer.service.common.exception.CustomerDomainBusinessException;
import com.melik.customer.service.common.usecase.VoidUseCaseHandler;
import com.melik.customer.service.common.valueobject.StatusType;
import com.melik.customer.service.customer.entity.Customer;
import com.melik.customer.service.customer.port.CustomerPort;
import com.melik.customer.service.customer.usecase.CustomerRetrieve;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class CustomerCancelVoidUseCaseHandler implements VoidUseCaseHandler<CustomerRetrieve> {

    private final CustomerPort customerPort;

    @Override
    public void handle(CustomerRetrieve customerRetrieve) {
        Optional<Customer> customerOptional = customerPort.retrieve(customerRetrieve);
        Customer customer = customerOptional.orElseThrow(() -> new CustomerDomainBusinessException("Not Found Customer!"));
        customer.setCancelDate(LocalDateTime.now());
        customer.setStatusType(StatusType.PASSIVE);
        customerPort.update(customer);
        log.info("Customer Cancelled for id:{}", customerRetrieve.getCustomerId());
    }
}
