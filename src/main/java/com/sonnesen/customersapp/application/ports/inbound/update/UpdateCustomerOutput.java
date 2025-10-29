package com.sonnesen.customersapp.application.ports.inbound.update;

import java.time.Instant;
import com.sonnesen.customersapp.application.domain.customer.Customer;

public record UpdateCustomerOutput(Long id, String name, String email, String phoneNumber,
        String cpf, boolean active, Instant createdAt, Instant updatedAt, Instant deletedAt) {

    public static UpdateCustomerOutput from(Customer customer) {
        return new UpdateCustomerOutput(customer.getId(), customer.getName(), customer.getEmail(),
                customer.getPhoneNumber(), customer.getCpf(), customer.isActive(),
                customer.getCreatedAt(), customer.getUpdatedAt(), customer.getDeletedAt());
    }
}
