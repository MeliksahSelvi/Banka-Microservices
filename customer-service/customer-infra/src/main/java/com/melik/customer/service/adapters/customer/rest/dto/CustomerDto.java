package com.melik.customer.service.adapters.customer.rest.dto;

import com.melik.customer.service.common.valueobject.StatusType;
import com.melik.customer.service.customer.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Long identityNo;
    private LocalDateTime cancelDate;
    private StatusType statusType;

    public static CustomerDto fromModel(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .identityNo(customer.getIdentityNo())
                .cancelDate(customer.getCancelDate())
                .statusType(customer.getStatusType())
                .build();
    }
}
