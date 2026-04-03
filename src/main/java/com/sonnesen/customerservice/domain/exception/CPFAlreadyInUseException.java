package com.sonnesen.customerservice.domain.exception;

public class CPFAlreadyInUseException extends DomainException {

    public CPFAlreadyInUseException(final String message) {
        super(message);
    }

}
