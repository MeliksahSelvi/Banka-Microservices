package com.melik.customer.service.customer;

import com.melik.customer.service.common.DomainComponent;
import com.melik.customer.service.common.usecase.UseCaseHandler;
import com.melik.customer.service.customer.entity.Customer;
import com.melik.customer.service.customer.port.CustomerPort;
import com.melik.customer.service.customer.usecase.CustomerRetrieveAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class CustomerRetrieveAllUseCaseHandler implements UseCaseHandler<List<Customer>, CustomerRetrieveAll> {

    private final CustomerPort customerPort;

    @Override
    public List<Customer> handle(CustomerRetrieveAll customerRetrieveAll) {
        List<Customer> customerList = customerPort.retrieveAll(customerRetrieveAll);
        log.info("Customers Retrieved for page and size values : {} , {} ", customerRetrieveAll.getPageOptional(), customerRetrieveAll.getSizeOptional());
        return customerList;
    }
}
