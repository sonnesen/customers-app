package com.sonnesen.customerservice.domain.exception;

public class InvalidEmailException extends DomainException {

    public InvalidEmailException(final String message) {
        super(message);
    }

}
