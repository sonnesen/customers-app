package com.sonnesen.customerservice.application.usecase.list;

import java.time.Instant;

import com.sonnesen.customerservice.domain.model.Customer;

public record CustomerListOutput(Long customerId, String name, String email, String phoneNumber, String cpf,
        boolean active, Instant createdAt, Instant updatedAt) {

    public static CustomerListOutput from(final Customer customer) {
        return new CustomerListOutput(customer.getId().getValue(), customer.getName(), customer.getEmail().getValue(),
                customer.getPhoneNumber().getValue(), customer.getCpf().getValue(), customer.isActive(),
                customer.getAuditInfo() != null ? customer.getAuditInfo().getCreatedAt() : null,
                customer.getAuditInfo() != null ? customer.getAuditInfo().getUpdatedAt() : null);
    }
}
