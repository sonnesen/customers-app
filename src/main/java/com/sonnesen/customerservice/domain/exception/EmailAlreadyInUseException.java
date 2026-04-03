package com.sonnesen.customerservice.domain.exception;

public class EmailAlreadyInUseException extends DomainException {

    public EmailAlreadyInUseException(final String message) {
        super(message);
    }

}
