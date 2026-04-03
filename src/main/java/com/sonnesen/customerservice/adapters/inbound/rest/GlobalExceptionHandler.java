package com.sonnesen.customerservice.adapters.inbound.rest;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sonnesen.customerservice.domain.exception.CPFAlreadyInUseException;
import com.sonnesen.customerservice.domain.exception.DomainException;
import com.sonnesen.customerservice.domain.exception.EmailAlreadyInUseException;
import com.sonnesen.customerservice.domain.exception.InvalidCPFFormatException;
import com.sonnesen.customerservice.domain.exception.InvalidEmailFormatException;
import com.sonnesen.customerservice.domain.exception.InvalidPhoneNumberFormatException;
import com.sonnesen.customerservice.domain.exception.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(final NotFoundException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Resource Not Found");
        return problemDetail;
    }

    @ExceptionHandler({ EmailAlreadyInUseException.class, CPFAlreadyInUseException.class })
    public ProblemDetail handleConflictExceptions(final DomainException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Conflict");
        return problemDetail;
    }

    @ExceptionHandler({ InvalidEmailFormatException.class, InvalidPhoneNumberFormatException.class,
            InvalidCPFFormatException.class })
    public ProblemDetail handleInvalidFormatExceptions(final DomainException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Bad Request");
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final var invalidFields = e.getFieldErrors().stream()
                .map(field -> new InvalidField(field.getField(), field.getDefaultMessage()))
                .toList();

        final var problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Bad Request");
        problemDetail.setDetail("One or more validation errors occurred");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("invalid-fields", invalidFields);
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(final Exception ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred.");
        problemDetail.setTitle("Internal Server Error");
        return problemDetail;
    }

    private record InvalidField(String name, String message) {
    }

}
