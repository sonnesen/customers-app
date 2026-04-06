package com.sonnesen.customerservice.domain.exception;

public abstract class DomainException extends RuntimeException {

    protected DomainException(final String message) {
        super(message, null, true, false);
    }
}
