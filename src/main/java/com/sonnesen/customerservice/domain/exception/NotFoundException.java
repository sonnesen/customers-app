package com.sonnesen.customerservice.domain.exception;

public class NotFoundException extends DomainException {

    public NotFoundException(final String message) {
        super(message);
    }
}
