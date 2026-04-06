package com.sonnesen.customerservice.domain.model;

import java.util.Objects;

import com.sonnesen.customerservice.domain.ValueObject;
import com.sonnesen.customerservice.domain.exception.InvalidPhoneNumberException;

public class PhoneNumber extends ValueObject {

    private static final String PHONE_NUMBER_REGEX = "\\d{10,11}";

    private final String value;

    private PhoneNumber(final String value) {
        if (value == null || !value.matches(PHONE_NUMBER_REGEX)) {
            throw new InvalidPhoneNumberException(
                    "Invalid phone number: " + value + ". It should be a string of 10 or 11 digits");
        }
        this.value = value;
    }

    public static PhoneNumber of(final String value) {
        return new PhoneNumber(value);
    }

    public String getValue() {
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
        if (!(obj instanceof PhoneNumber))
            return false;
        PhoneNumber other = (PhoneNumber) obj;
        return Objects.equals(value, other.value);
    }

}
