package com.sonnesen.customerservice.application.usecase.activate;

import java.util.Objects;

import com.sonnesen.customerservice.domain.exception.NotFoundException;
import com.sonnesen.customerservice.domain.model.CustomerId;
import com.sonnesen.customerservice.ports.inbound.activate.ActivateCustomerUseCase;
import com.sonnesen.customerservice.ports.outbound.persistence.repository.CustomerRepositoryPort;

public class DefaultActivateCustomerUseCase implements ActivateCustomerUseCase {

    private final CustomerRepositoryPort customerRepository;

    public DefaultActivateCustomerUseCase(final CustomerRepositoryPort customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository,
                "customerRepository must not be null");
    }

    @Override
    public void execute(final CustomerId customerId) {
        Objects.requireNonNull(customerId, "customerId must not be null");
        final var customer = customerRepository.findById(customerId).orElseThrow(
                () -> new NotFoundException("Customer with id " + customerId + " not found"));
        customer.activate();
        customerRepository.update(customer);
    }
}
