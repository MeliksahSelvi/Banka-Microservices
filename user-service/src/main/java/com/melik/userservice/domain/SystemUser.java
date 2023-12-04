package com.melik.userservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

@Entity
@Getter
@Setter
@Table(name = "SYSTEM_USER")
public class SystemUser {

    @SequenceGenerator(name = "SystemUser", sequenceName = "SYSTEM_USER_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "SystemUser")
    @Column
    @Id
    private Long id;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ID_CUSTOMER")
    private Long customerId;
}
