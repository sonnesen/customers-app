package com.sonnesen.customerservice.application.usecase.get;

import java.util.Objects;

import com.sonnesen.customerservice.domain.exception.NotFoundException;
import com.sonnesen.customerservice.ports.outbound.persistence.repository.CustomerRepositoryPort;

public class GetCustomerByIdUseCase {

    public final CustomerRepositoryPort customerRepository;

    public GetCustomerByIdUseCase(final CustomerRepositoryPort customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository, "customerRepository must not be null");
    }

    public CustomerOutput execute(final Long customerId) {
        Objects.requireNonNull(customerId, "customerId must not be null");
        final var customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer with id " + customerId + " not found"));
        return CustomerOutput.from(customer);
    }
}
