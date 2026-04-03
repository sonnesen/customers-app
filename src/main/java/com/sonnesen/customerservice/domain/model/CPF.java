package com.sonnesen.customerservice.domain.model;

import java.util.regex.Pattern;

import com.sonnesen.customerservice.domain.ValueObject;
import com.sonnesen.customerservice.domain.exception.InvalidCPFFormatException;

public class CPF extends ValueObject {

    private static final Pattern CPF_ONLY_DIGITS_PATTERN = Pattern.compile("\\d{11}");

    private final String value;

    private CPF(final String value) {
        if (value == null || !CPF_ONLY_DIGITS_PATTERN.matcher(value).matches()) {
            throw new InvalidCPFFormatException(
                    "Invalid CPF format: " + value + ". It should contain exactly 11 digits.");
        }
        this.value = value;
    }

    public static CPF of(final String value) {
        return new CPF(value);
    }

    public String getValue() {
        return value;
    }

}
