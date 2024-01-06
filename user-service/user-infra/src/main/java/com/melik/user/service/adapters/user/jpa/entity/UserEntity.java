package com.melik.user.service.adapters.user.jpa.entity;

import com.melik.user.service.common.model.AbstractEntity;
import com.melik.user.service.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

@Entity
@Getter
@Setter
@Table(name = "\"user\"")
public class UserEntity extends AbstractEntity {

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    public User toModel() {
        return User.builder()
                .id(super.getId())
                .statusType(super.getStatusType())
                .email(email)
                .password(password)
                .build();
    }
}
