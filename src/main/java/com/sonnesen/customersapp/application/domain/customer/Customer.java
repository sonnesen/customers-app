package com.sonnesen.customersapp.application.domain.customer;

import java.time.Instant;

public class Customer {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String cpf;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Customer(Long id, String name, String email, String phoneNumber, String cpf,
            boolean active, Instant createdAt, Instant updatedAt, Instant deletedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Customer newCustomer(String name, String email, String phoneNumber, String cpf) {
        var now = Instant.now();
        var isActive = true;
        return new Customer(null, name, email, phoneNumber, cpf, isActive, now, now, null);
    }

    public static Customer with(Long id, String name, String email, String phoneNumber, String cpf,
            boolean active, Instant createdAt, Instant updatedAt, Instant deletedAt) {
        return new Customer(id, name, email, phoneNumber, cpf, active, createdAt, updatedAt, deletedAt);
    }

    public Customer update(String name, String email, String phoneNumber, String cpf, boolean active) {
        if (active) {
            activate();
        } else {
            deactivate();
        }
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
        this.updatedAt = Instant.now();
        return this;
    }

    public Customer activate() {
        this.active = true;
        this.deletedAt = null;
        this.updatedAt = Instant.now();
        return this;
    }

    public Customer deactivate() {
        if (this.deletedAt == null) {
            this.deletedAt = Instant.now();
        }
        this.active = false;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCpf() {
        return cpf;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

}
