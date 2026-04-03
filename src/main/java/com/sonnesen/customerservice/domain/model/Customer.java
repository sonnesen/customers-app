package com.sonnesen.customerservice.domain.model;

import java.time.Instant;

import com.sonnesen.customerservice.domain.AggregateRoot;

public class Customer extends AggregateRoot<CustomerId> {

    private String name;
    private Email email;
    private PhoneNumber phoneNumber;
    private CPF cpf;
    private boolean active;
    private AuditInfo auditInfo;

    private Customer(CustomerId id, String name, Email email, PhoneNumber phoneNumber, CPF cpf,
            boolean active, AuditInfo auditInfo) {
        super(id);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
        this.active = active;
        this.auditInfo = auditInfo;
    }

    public static Customer newCustomer(String name, Email email, PhoneNumber phoneNumber, CPF cpf) {
        var now = Instant.now();
        var isActive = true;
        var auditInfo = AuditInfo.of(now, now);
        return new Customer(null, name, email, phoneNumber, cpf, isActive, auditInfo);
    }

    public static Customer with(CustomerId id, String name, Email email, PhoneNumber phoneNumber, CPF cpf,
            boolean active, AuditInfo auditInfo) {
        return new Customer(id, name, email, phoneNumber, cpf, active, auditInfo);
    }

    public Customer update(String name, Email email, PhoneNumber phoneNumber, CPF cpf) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
        this.auditInfo = this.auditInfo.withUpdatedAt(Instant.now());
        return this;
    }

    public Customer activate() {
        if (!this.active) {
            this.active = true;
            this.auditInfo = this.auditInfo.withUpdatedAt(Instant.now());
        }
        return this;
    }

    public Customer deactivate() {
        if (this.active) {
            this.active = false;
            this.auditInfo = this.auditInfo.withUpdatedAt(Instant.now());
        }
        return this;
    }

    public String getName() {
        return name;
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
