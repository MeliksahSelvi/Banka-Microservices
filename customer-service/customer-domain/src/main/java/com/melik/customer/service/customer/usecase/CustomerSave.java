package com.melik.customer.service.customer.usecase;

import com.melik.customer.service.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

@Data
@Builder
public class CustomerSave implements UseCase {

    private String firstName;
    private String lastName;
    private Long identityNo;
    private String password;
}
