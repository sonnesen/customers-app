package com.sonnesen.customerservice.application.usecase.create;

import java.util.Objects;

import com.sonnesen.customerservice.domain.exception.CPFAlreadyInUseException;
import com.sonnesen.customerservice.domain.exception.EmailAlreadyInUseException;
import com.sonnesen.customerservice.domain.model.Customer;
import com.sonnesen.customerservice.ports.inbound.create.CreateCustomerUseCase;
import com.sonnesen.customerservice.ports.outbound.persistence.repository.CustomerRepositoryPort;

public class DefaultCreateCustomerUseCase implements CreateCustomerUseCase {

    private final CustomerRepositoryPort customerRepository;

    public DefaultCreateCustomerUseCase(final CustomerRepositoryPort customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository, "customerRepository must not be null");
    }

    @Override
    public CreateCustomerOutput execute(final CreateCustomerCommand command) {
        Objects.requireNonNull(command, "command must not be null");
        if (customerRepository.existsByEmail(command.email())) {
            throw new EmailAlreadyInUseException("Email " + command.email() + " is already in use");
        }
        if (customerRepository.existsByCpf(command.cpf())) {
            throw new CPFAlreadyInUseException("CPF " + command.cpf() + " is already in use");
        }
        final var toPersist = Customer.newCustomer(command.name(), command.email(),
                command.phoneNumber(),
                command.cpf());
        final var persisted = customerRepository.create(toPersist);
        return new CreateCustomerOutput(persisted.getId().getValue());
    }

}
