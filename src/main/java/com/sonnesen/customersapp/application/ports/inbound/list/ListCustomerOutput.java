package com.sonnesen.customersapp.application.ports.inbound.list;

import java.time.Instant;
import com.sonnesen.customersapp.application.domain.customer.Customer;

public record ListCustomerOutput(Long id, String name, String email, String phoneNumber, String cpf, boolean active,
        Instant createdAt, Instant updatedAt, Instant deletedAt) {

    public static ListCustomerOutput from(Customer customer) {
        return new ListCustomerOutput(customer.getId(), customer.getName(), customer.getEmail(),
                customer.getPhoneNumber(), customer.getCpf(), customer.isActive(),
                customer.getCreatedAt(), customer.getUpdatedAt(), customer.getDeletedAt());
    }

}
