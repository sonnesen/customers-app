package com.sonnesen.customerservice.domain.model;

import com.sonnesen.customerservice.domain.ValueObject;
import com.sonnesen.customerservice.domain.exception.InvalidPhoneNumberFormatException;

public class PhoneNumber extends ValueObject {

    private static final String PHONE_NUMBER_REGEX = "\\+?[1-9]\\d{1,14}";

    private final String value;

    private PhoneNumber(final String value) {
        if (value == null || !value.matches(PHONE_NUMBER_REGEX)) {
            throw new InvalidPhoneNumberFormatException("Invalid phone number format: " + value);
        }
        this.value = value;
    }

    public static PhoneNumber of(final String value) {
        return new PhoneNumber(value);
    }

    public String getValue() {
        return value;
    }

}
