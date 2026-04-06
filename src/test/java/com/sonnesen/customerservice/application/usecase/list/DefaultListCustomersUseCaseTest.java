package com.sonnesen.customerservice.application.usecase.list;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sonnesen.customerservice.domain.model.AuditInfo;
import com.sonnesen.customerservice.domain.model.CPF;
import com.sonnesen.customerservice.domain.model.Customer;
import com.sonnesen.customerservice.domain.model.CustomerId;
import com.sonnesen.customerservice.domain.model.CustomerName;
import com.sonnesen.customerservice.domain.model.Email;
import com.sonnesen.customerservice.domain.model.Page;
import com.sonnesen.customerservice.domain.model.Pagination;
import com.sonnesen.customerservice.domain.model.PhoneNumber;
import com.sonnesen.customerservice.ports.outbound.persistence.repository.CustomerRepositoryPort;

@ExtendWith(MockitoExtension.class)
class DefaultListCustomersUseCaseTest {

    @InjectMocks
    DefaultListCustomersUseCase useCase;

    @Mock
    CustomerRepositoryPort customerRepository;

    @Test
    void shouldThrowNullPointerExceptionWhenPageIsNull() {
        // Arrange

        // Act
        final var exception = assertThrows(NullPointerException.class, () -> useCase.execute(null));

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("page must not be null");
    }

    @Test
    void shouldListCustomers() {
        // Arrange
        final var currentPage = 0;
        final var perPage = 10;
        final var page = new Page(currentPage, perPage);
        final var customerId = CustomerId.of(1L);
        final var customerName = CustomerName.of("John Doe");
        final var email = Email.of("john.doe@example.com");
        final var phoneNumber = PhoneNumber.of("1234567890");
        final var cpf = CPF.of("12345678900");
        final var now = Instant.now();
        final var auditInfo = AuditInfo.of(now, now);
        final var customers = List.of(
                Customer.with(customerId, customerName, email, phoneNumber, cpf, true, auditInfo));
        final var totalItems = customers.size();
        final var customerPagination = new Pagination<Customer>(currentPage, perPage, totalItems, customers);

        when(customerRepository.findAll(page)).thenReturn(customerPagination);

        // Act
        final var result = useCase.execute(page);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.currentPage()).isEqualTo(currentPage);
        assertThat(result.perPage()).isEqualTo(perPage);
        assertThat(result.totalItems()).isEqualTo(totalItems);
    }
}
