package com.sonnesen.customerservice.domain.exception;

public class InvalidCPFFormatException extends DomainException {

    public InvalidCPFFormatException(final String message) {
        super(message);
    }

}
