package com.sonnesen.customerservice.domain.exception;

public class InvalidEmailFormatException extends DomainException {

    public InvalidEmailFormatException(final String message) {
        super(message);
    }

}
