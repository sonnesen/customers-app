package com.sonnesen.customerservice.application.usecase.update;

import java.util.Objects;

import com.sonnesen.customerservice.domain.exception.NotFoundException;
import com.sonnesen.customerservice.ports.outbound.persistence.repository.CustomerRepositoryPort;

public class UpdateCustomerUseCase {

    private final CustomerRepositoryPort customerRepository;

    public UpdateCustomerUseCase(final CustomerRepositoryPort customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository, "customerRepository must not be null");
    }

    public void execute(final UpdateCustomerCommand command) {
        Objects.requireNonNull(command, "command must not be null");
        final var customer = customerRepository.findById(command.customerId().getValue()).orElseThrow(
                () -> new NotFoundException("Customer with id " + command.customerId() + " not found"));
        customer.update(command.name(), command.email(), command.phoneNumber(), command.cpf());
        customerRepository.update(customer);
    }
}
