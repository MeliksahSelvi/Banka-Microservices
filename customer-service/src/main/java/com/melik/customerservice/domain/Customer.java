package com.melik.customerservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author mselvi
 * @Created 31.08.2023
 */

@Entity
@Getter
@Setter
@Table(name = "CUSTOMER")
public class Customer {

    @SequenceGenerator(name = "Customer", sequenceName = "CUSTOMER_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "Customer")
    @Column
    @Id
    private Long id;

    @Column(name = "FIRST_NAME", length = 100, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 100, nullable = false)
    private String lastName;

    @Column(name = "IDENTITY_NO", nullable = false)
    private Long identityNo;

    @Column(name = "PASSWORD", nullable = false)
    private String password;
}
