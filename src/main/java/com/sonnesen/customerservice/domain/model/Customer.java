package com.sonnesen.customerservice.domain.model;

import java.time.Instant;

import com.sonnesen.customerservice.domain.AggregateRoot;
import com.sonnesen.customerservice.domain.exception.InvalidCPFException;
import com.sonnesen.customerservice.domain.exception.InvalidCustomerNameException;
import com.sonnesen.customerservice.domain.exception.InvalidEmailException;
import com.sonnesen.customerservice.domain.exception.InvalidPhoneNumberException;

public class Customer extends AggregateRoot<CustomerId> {

    private final CustomerName customerName;
    private final Email email;
    private final PhoneNumber phoneNumber;
    private final CPF cpf;
    private final boolean active;
    private final AuditInfo auditInfo;

    private Customer(final CustomerId id, final CustomerName customerName, final Email email,
            final PhoneNumber phoneNumber, final CPF cpf, final boolean active, final AuditInfo auditInfo) {
        super(id);
        this.customerName = customerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
        this.active = active;
        this.auditInfo = auditInfo;
        this.validate();
    }

    private void validate() {
        if (customerName == null) {
            throw new InvalidCustomerNameException("Invalid customer name: " + customerName);
        }

        if (email == null) {
            throw new InvalidEmailException("Invalid customer email: " + email);
        }

        if (phoneNumber == null) {
            throw new InvalidPhoneNumberException("Invalid customer phone number: " + phoneNumber);
        }

        if (cpf == null) {
            throw new InvalidCPFException("Invalid customer CPF: " + cpf);
        }
    }

    public static Customer newCustomer(final CustomerName customerName, final Email email,
            final PhoneNumber phoneNumber, final CPF cpf) {
        var now = Instant.now();
        var isActive = true;
        var auditInfo = AuditInfo.of(now, now);
        return new Customer(null, customerName, email, phoneNumber, cpf, isActive, auditInfo);
    }

    public static Customer with(final CustomerId id, final CustomerName customerName, final Email email,
            final PhoneNumber phoneNumber, final CPF cpf, final boolean active, final AuditInfo auditInfo) {
        return new Customer(id, customerName, email, phoneNumber, cpf, active, auditInfo);
    }

    public Customer update(final CustomerName customerName, final Email email, final PhoneNumber phoneNumber,
            final CPF cpf) {
        return new Customer(this.getId(), customerName, email, phoneNumber, cpf, this.active,
                this.auditInfo.withUpdatedAt(Instant.now()));
    }

    public Customer activate() {
        if (!this.active) {
            return new Customer(this.getId(), this.customerName, this.email, this.phoneNumber, this.cpf, true,
                    this.auditInfo.withUpdatedAt(Instant.now()));
        }
        return this;
    }

    public Customer deactivate() {
        if (this.active) {
            return new Customer(this.getId(), this.customerName, this.email, this.phoneNumber, this.cpf, false,
                    this.auditInfo.withUpdatedAt(Instant.now()));
        }
        return this;
    }

    public CustomerName getCustomerName() {
        return customerName;
    }

    public Email getEmail() {
        return email;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public CPF getCpf() {
        return cpf;
    }

    public boolean isActive() {
        return active;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

}
