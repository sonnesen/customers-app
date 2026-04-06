package com.sonnesen.customerservice.domain.model;

import java.util.Objects;

import com.sonnesen.customerservice.domain.Identifier;
import com.sonnesen.customerservice.domain.exception.InvalidCustomerIdException;

public class CustomerId extends Identifier<Long> {

    private final Long value;

    private CustomerId(final Long value) {
        this.value = value;
    }

    public static CustomerId of(final Long value) {
        if (value == null || value <= 0) {
            throw new InvalidCustomerIdException("Invalid customer ID: " + value);
        }
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
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof CustomerId))
            return false;
        final CustomerId other = (CustomerId) obj;
        return Objects.equals(value, other.value);
    }

}
