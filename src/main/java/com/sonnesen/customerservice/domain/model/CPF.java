package com.sonnesen.customerservice.domain.model;

import java.util.Objects;
import java.util.regex.Pattern;

import com.sonnesen.customerservice.domain.ValueObject;
import com.sonnesen.customerservice.domain.exception.InvalidCPFException;

public class CPF extends ValueObject {

    private static final Pattern CPF_ONLY_DIGITS_PATTERN = Pattern.compile("\\d{11}");

    private final String value;

    private CPF(final String value) {
        if (value == null || !CPF_ONLY_DIGITS_PATTERN.matcher(value).matches()) {
            throw new InvalidCPFException(
                    "Invalid CPF: " + value + ". It should contain exactly 11 digits.");
        }
        this.value = value;
    }

    public static CPF of(final String value) {
        return new CPF(value);
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
        if (!(obj instanceof CPF))
            return false;
        CPF other = (CPF) obj;
        return Objects.equals(value, other.value);
    }

}
