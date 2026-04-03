package com.sonnesen.customerservice.application.usecase.deactivate;

import java.util.Objects;

import com.sonnesen.customerservice.domain.exception.NotFoundException;
import com.sonnesen.customerservice.ports.outbound.persistence.repository.CustomerRepositoryPort;

public class DeactivateCustomerUseCase {

    public final CustomerRepositoryPort customerRepository;

    public DeactivateCustomerUseCase(final CustomerRepositoryPort customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository,
                "customerRepository must not be null");
    }

    public void execute(final Long customerId) {
        Objects.requireNonNull(customerId, "customerId must not be null");
        final var customer = customerRepository.findById(customerId).orElseThrow(
                () -> new NotFoundException("Customer with id " + customerId + " not found"));
        customer.deactivate();
        customerRepository.update(customer);
    }
}
