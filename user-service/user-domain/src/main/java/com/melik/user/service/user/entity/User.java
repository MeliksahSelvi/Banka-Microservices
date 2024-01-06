package com.melik.user.service.user.entity;

import com.melik.user.service.common.model.BaseEntity;
import com.melik.user.service.common.valueobject.StatusType;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

public class User extends BaseEntity<Long> {

    private String email;
    private String password;

    private User(Builder builder) {
        setId(builder.id);
        setStatusType(builder.statusType);
        email = builder.email;
        password = builder.password;
    }

    public static Builder builder(){
        return new Builder();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static final class Builder {
        private Long id;
        private String email;
        private String password;
        private StatusType statusType;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder statusType(StatusType val) {
            statusType = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
