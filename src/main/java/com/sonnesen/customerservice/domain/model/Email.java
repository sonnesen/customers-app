package com.sonnesen.customerservice.domain.model;

import java.util.Objects;
import java.util.regex.Pattern;

import com.sonnesen.customerservice.domain.ValueObject;
import com.sonnesen.customerservice.domain.exception.InvalidEmailException;

public class Email extends ValueObject {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private final String value;

    private Email(final String value) {
        if (value == null || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new InvalidEmailException("Invalid email: " + value);
        }
        this.value = value;
    }

    public static Email of(final String value) {
        return new Email(value);
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
        if (!(obj instanceof Email))
            return false;
        Email other = (Email) obj;
        return Objects.equals(value, other.value);
    }

}
