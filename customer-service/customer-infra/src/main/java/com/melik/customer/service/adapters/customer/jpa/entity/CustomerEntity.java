package com.melik.customer.service.adapters.customer.jpa.entity;

import com.melik.customer.service.common.model.AbstractEntity;
import com.melik.customer.service.common.valueobject.StatusType;
import com.melik.customer.service.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

@Entity
@Getter
@Setter
@Table(name = "customer")
public class CustomerEntity extends AbstractEntity {

    @Column(name = "FIRST_NAME", length = 100, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 100, nullable = false)
    private String lastName;

    @Column(name = "IDENTITY_NO", nullable = false,unique = true)
    private Long identityNo;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "CANCEL_DATE")
    private LocalDateTime cancelDate;

    public Customer toModel(){
        return Customer.builder()
                .id(super.getId())
                .statusType(super.getStatusType())
                .firstName(firstName)
                .lastName(lastName)
                .identityNo(identityNo)
                .cancelDate(cancelDate)
                .build();
    }
}
