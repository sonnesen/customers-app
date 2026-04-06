package com.sonnesen.customerservice.application.usecase.update;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sonnesen.customerservice.application.usecase.update.DefaultUpdateCustomerUseCase;
import com.sonnesen.customerservice.application.usecase.update.UpdateCustomerCommand;
import com.sonnesen.customerservice.domain.exception.NotFoundException;
import com.sonnesen.customerservice.domain.model.AuditInfo;
import com.sonnesen.customerservice.domain.model.CPF;
import com.sonnesen.customerservice.domain.model.Customer;
import com.sonnesen.customerservice.domain.model.CustomerId;
import com.sonnesen.customerservice.domain.model.CustomerName;
import com.sonnesen.customerservice.domain.model.Email;
import com.sonnesen.customerservice.domain.model.PhoneNumber;
import com.sonnesen.customerservice.ports.outbound.persistence.repository.CustomerRepositoryPort;

@ExtendWith(MockitoExtension.class)
class DefaultUpdateCustomerUseCaseTest {

    @InjectMocks
    DefaultUpdateCustomerUseCase useCase;

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
    void shouldThrowNotFoundExceptionWhenCustomerDoesNotExist() {
        // Arrange
        final var customerId = CustomerId.of(1L);
        final var name = CustomerName.of("John Doe");
        final var email = Email.of("john.doe@mail.com");
        final var phoneNumber = PhoneNumber.of("47999999999");
        final var cpf = CPF.of("12345678900");
        final var command = new UpdateCustomerCommand(customerId, name, email, phoneNumber, cpf);

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(command));

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Customer with id " + customerId + " not found");
    }

    @Test
    void shouldUpdateCustomerSuccessfully() {
        // Arrange
        final var customerId = CustomerId.of(1L);
        final var name = CustomerName.of("John Doe");
        final var email = Email.of("john.doe@mail.com");
        final var phoneNumber = PhoneNumber.of("47999999999");
        final var cpf = CPF.of("12345678900");
        final var command = new UpdateCustomerCommand(customerId, name, email, phoneNumber, cpf);
        final var now = Instant.now();
        final var auditInfo = AuditInfo.of(now, now);
        final var persistedCustomer = Customer.with(customerId, name, email, phoneNumber, cpf, true, auditInfo);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(persistedCustomer));

        // Act
        useCase.execute(command);

        // Assert
        verify(customerRepository).update(persistedCustomer);
    }
}
