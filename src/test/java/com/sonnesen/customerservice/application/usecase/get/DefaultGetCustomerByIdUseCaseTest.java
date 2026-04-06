package com.sonnesen.customerservice.application.usecase.get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sonnesen.customerservice.application.usecase.get.DefaultGetCustomerByIdUseCase;
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
class DefaultGetCustomerByIdUseCaseTest {

    @InjectMocks
    DefaultGetCustomerByIdUseCase useCase;

    @Mock
    CustomerRepositoryPort customerRepository;

    @Test
    void shouldThrowNullPointerExceptionWhenCustomerIdIsNull() {
        // Arrange

        // Act
        final var exception = assertThrows(NullPointerException.class, () -> useCase.execute(null));

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("customerId must not be null");
    }

    @Test
    void shouldThrowNotFoundExceptionWhenCustomerDoesNotExist() {
        // Arrange
        final var customerId = CustomerId.of(1L);

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(customerId));

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Customer with id " + customerId + " not found");
    }

    @Test
    void shouldReturnCustomerOutputWhenCustomerExists() {
        // Arrange
        final var customerId = CustomerId.of(1L);
        final var customerName = CustomerName.of("John Doe");
        final var email = Email.of("john.doe@example.com");
        final var phoneNumber = PhoneNumber.of("1234567890");
        final var cpf = CPF.of("12345678900");
        final var now = java.time.Instant.now();
        final var auditInfo = AuditInfo.of(now, now);
        final var customer = Customer.with(customerId, customerName, email, phoneNumber, cpf, true, auditInfo);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act
        final var result = useCase.execute(customerId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.customerId()).isEqualTo(customerId.getValue());
        assertThat(result.name()).isEqualTo(customerName.getValue());
        assertThat(result.email()).isEqualTo(email.getValue());
        assertThat(result.phoneNumber()).isEqualTo(phoneNumber.getValue());
        assertThat(result.cpf()).isEqualTo(cpf.getValue());
        assertThat(result.active()).isTrue();
    }
}
