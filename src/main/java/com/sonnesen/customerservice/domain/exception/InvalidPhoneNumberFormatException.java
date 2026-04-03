package com.sonnesen.customerservice.domain.exception;

public class InvalidPhoneNumberFormatException extends DomainException {

    public InvalidPhoneNumberFormatException(final String message) {
        super(message);
    }

}
