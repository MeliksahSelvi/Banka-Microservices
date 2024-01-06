package com.melik.customer.service.customer.entity;

import com.melik.customer.service.common.model.BaseEntity;
import com.melik.customer.service.common.valueobject.StatusType;

import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

public class Customer extends BaseEntity<Long> {

    private final String firstName;
    private final String lastName;
    private final Long identityNo;
    private String password;
    private LocalDateTime cancelDate;

    public static Builder builder() {
        return new Builder();
    }

    private Customer(Builder builder) {
        setId(builder.id);
        setStatusType(builder.statusType);
        firstName= builder.firstName;
        lastName= builder.lastName;
        identityNo= builder.identityNo;
        password=builder.password;
        cancelDate=builder.cancelDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getIdentityNo() {
        return identityNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(LocalDateTime cancelDate) {
        this.cancelDate = cancelDate;
    }

    public static final class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private Long identityNo;
        private String password;
        private LocalDateTime cancelDate;
        private StatusType statusType;

        private Builder() {

        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder firstName(String val) {
            firstName = val;
            return this;
        }

        public Builder lastName(String val) {
            lastName = val;
            return this;
        }

        public Builder identityNo(Long val) {
            identityNo = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder cancelDate(LocalDateTime val) {
            cancelDate = val;
            return this;
        }

        public Builder statusType(StatusType val) {
            statusType = val;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}
