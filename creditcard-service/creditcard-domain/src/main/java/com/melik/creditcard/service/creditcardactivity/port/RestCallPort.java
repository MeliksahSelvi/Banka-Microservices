package com.melik.creditcard.service.creditcardactivity.port;

import com.melik.creditcard.service.creditcardactivity.usecase.CustomerResponseDto;

/**
 * @Author mselvi
 * @Created 04.01.2024
 */

public interface RestCallPort {

    CustomerResponseDto restCallToCustomerService(Long customerId);
}
