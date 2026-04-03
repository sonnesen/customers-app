package com.sonnesen.customerservice.domain.model;

import java.util.Objects;

import com.sonnesen.customerservice.domain.Identifier;

public class CustomerId extends Identifier<Long> {

    private final Long value;

    private CustomerId(Long value) {
        this.value = value;
    }

    public static CustomerId of(Long value) {
        return new CustomerId(value);
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof CustomerId))
            return false;
        CustomerId other = (CustomerId) obj;
        return Objects.equals(value, other.value);
    }

}
