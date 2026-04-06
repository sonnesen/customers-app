package com.sonnesen.customerservice.domain.model;

import java.util.Objects;

import com.sonnesen.customerservice.domain.ValueObject;
import com.sonnesen.customerservice.domain.exception.InvalidCustomerNameException;

public class CustomerName extends ValueObject {

    private final String value;

    private CustomerName(final String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidCustomerNameException("Customer name cannot be null or blank");
        }
        this.value = value.trim();
    }

    public static CustomerName of(final String value) {
        return new CustomerName(value);
    }

    public String getValue() {
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
        if (!(obj instanceof CustomerName))
            return false;
        final CustomerName other = (CustomerName) obj;
        return Objects.equals(value, other.value);
    }

}
