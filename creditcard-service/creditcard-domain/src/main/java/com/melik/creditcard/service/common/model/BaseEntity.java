package com.melik.creditcard.service.common.model;

import com.melik.creditcard.service.common.valueobject.StatusType;

import java.util.Objects;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

public abstract class BaseEntity<ID> {
    private ID id;
    private StatusType statusType;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity<?> that = (BaseEntity<?>) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
