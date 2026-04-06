package com.sonnesen.customerservice.application.usecase.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sonnesen.customerservice.application.usecase.create.CreateCustomerCommand;
import com.sonnesen.customerservice.application.usecase.create.DefaultCreateCustomerUseCase;
import com.sonnesen.customerservice.domain.exception.CPFAlreadyInUseException;
import com.sonnesen.customerservice.domain.exception.EmailAlreadyInUseException;
import com.sonnesen.customerservice.domain.model.AuditInfo;
import com.sonnesen.customerservice.domain.model.CPF;
import com.sonnesen.customerservice.domain.model.Customer;
import com.sonnesen.customerservice.domain.model.CustomerId;
import com.sonnesen.customerservice.domain.model.CustomerName;
import com.sonnesen.customerservice.domain.model.Email;
import com.sonnesen.customerservice.domain.model.PhoneNumber;
import com.sonnesen.customerservice.ports.outbound.persistence.repository.CustomerRepositoryPort;

@ExtendWith(MockitoExtension.class)
class DefaultCreateCustomerUseCaseTest {

    @InjectMocks
    DefaultCreateCustomerUseCase useCase;

    @Mock
    CustomerRepositoryPort customerRepository;

    @Test
    void shouldThrowNullPointerExceptionWhenCommandIsNull() {
        // Arrange

        // Act
        final var exception = assertThrows(NullPointerException.class, () -> useCase.execute(null));

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("command must not be null");
    }

    @Test
    void shouldThrowEmailAlreadyInUseExceptionWhenEmailIsAlreadyInUse() {
        // Arrange
        final var name = CustomerName.of("John Doe");
        final var email = Email.of("john.doe@mail.com");
        final var phoneNumber = PhoneNumber.of("47999999999");
        final var cpf = CPF.of("12345678900");
        final var command = new CreateCustomerCommand(name, email, phoneNumber, cpf);

        when(customerRepository.existsByEmail(email)).thenReturn(true);

        // Act
        final var exception = assertThrows(EmailAlreadyInUseException.class, () -> useCase.execute(command));

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Email " + command.email() + " is already in use");
    }

    @Test
    void shouldThrowCPFAlreadyInUseExceptionWhenCPFIsAlreadyInUse() {
        // Arrange
        final var name = CustomerName.of("John Doe");
        final var email = Email.of("john.doe@mail.com");
        final var phoneNumber = PhoneNumber.of("47999999999");
        final var cpf = CPF.of("12345678900");
        final var command = new CreateCustomerCommand(name, email, phoneNumber, cpf);

        when(customerRepository.existsByEmail(command.email())).thenReturn(false);
        when(customerRepository.existsByCpf(command.cpf())).thenReturn(true);

        // Act
        final var exception = assertThrows(CPFAlreadyInUseException.class, () -> useCase.execute(command));

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("CPF " + command.cpf() + " is already in use");
    }

    @Test
    void shouldCreateCustomerSuccessfully() {
        // Arrange
        final var customerId = CustomerId.of(1L);
        final var name = CustomerName.of("John Doe");
        final var email = Email.of("john.doe@mail.com");
        final var phoneNumber = PhoneNumber.of("47999999999");
        final var cpf = CPF.of("12345678900");
        final var command = new CreateCustomerCommand(name, email, phoneNumber, cpf);
        final var now = Instant.now();
        final var auditInfo = AuditInfo.of(now, now);
        final var persistedCustomer = Customer.with(customerId, name, email, phoneNumber, cpf, true, auditInfo);

        when(customerRepository.existsByEmail(command.email())).thenReturn(false);
        when(customerRepository.existsByCpf(command.cpf())).thenReturn(false);
        when(customerRepository.create(any(Customer.class))).thenReturn(persistedCustomer);

        // Act
        final var customer = useCase.execute(command);

        // Assert
        assertThat(customer).isNotNull();
        assertThat(customer.customerId()).isNotNull();
        assertThat(customer.customerId()).isEqualTo(customerId.getValue());
    }
}