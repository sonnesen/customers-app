package com.sonnesen.customersapp.infrastructure.adapters.outbound.persistence.entity;

import java.time.Instant;
import com.sonnesen.customersapp.application.domain.customer.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class CustomerJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "version", nullable = true)
    private Instant deletedAt;

    public CustomerJpaEntity() {}

    public CustomerJpaEntity(Long id, String name, String email, String phoneNumber, String cpf, boolean active, Instant createdAt, Instant updatedAt, Instant deletedAt) {
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

    public static CustomerJpaEntity of(Customer customer) {
        return new CustomerJpaEntity(
            customer.getId(),
            customer.getName(),
            customer.getEmail(),
            customer.getPhoneNumber(),
            customer.getCpf(),
            customer.isActive(),
            customer.getCreatedAt(),
            customer.getUpdatedAt(),
            customer.getDeletedAt()
        );
    }

    public Customer toDomain() {
        return Customer.with(
            id,
            name,
            email,
            phoneNumber,
            cpf,
            active,
            createdAt,
            updatedAt,
            deletedAt
        );
    }

}
