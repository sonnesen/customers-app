package com.sonnesen.customerservice.domain.exception;

public class InvalidPhoneNumberException extends DomainException {

    public InvalidPhoneNumberException(final String message) {
        super(message);
    }

}
