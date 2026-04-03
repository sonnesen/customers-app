package com.sonnesen.customerservice.domain.model;

import java.util.regex.Pattern;

import com.sonnesen.customerservice.domain.ValueObject;
import com.sonnesen.customerservice.domain.exception.InvalidEmailFormatException;

public class Email extends ValueObject {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private final String value;

    private Email(final String value) {
        if (value == null || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new InvalidEmailFormatException("Invalid email format: " + value);
        }
        this.value = value;
    }

    public static Email of(final String value) {
        return new Email(value);
    }

    public String getValue() {
        return value;
    }

}
