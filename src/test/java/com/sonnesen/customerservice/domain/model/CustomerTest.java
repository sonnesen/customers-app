package com.sonnesen.customerservice.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.sonnesen.customerservice.domain.exception.InvalidCPFException;
import com.sonnesen.customerservice.domain.exception.InvalidCustomerNameException;
import com.sonnesen.customerservice.domain.exception.InvalidEmailException;
import com.sonnesen.customerservice.domain.exception.InvalidPhoneNumberException;
import com.sonnesen.customerservice.domain.model.AuditInfo;
import com.sonnesen.customerservice.domain.model.CPF;
import com.sonnesen.customerservice.domain.model.Customer;
import com.sonnesen.customerservice.domain.model.CustomerId;
import com.sonnesen.customerservice.domain.model.CustomerName;
import com.sonnesen.customerservice.domain.model.Email;
import com.sonnesen.customerservice.domain.model.PhoneNumber;

class CustomerTest {

    @Test
    void shouldCreateANewCustomer() {
        final var customerName = CustomerName.of("John Doe");
        final var email = Email.of("john@example.com");
        final var phoneNumber = PhoneNumber.of("47999999999");
        final var cpf = CPF.of("12345678901");

        final var customer = Customer.newCustomer(customerName, email, phoneNumber, cpf);

        assertThat(customer).isNotNull()
                .returns(customerName, Customer::getCustomerName)
                .returns(email, Customer::getEmail)
                .returns(phoneNumber, Customer::getPhoneNumber)
                .returns(cpf, Customer::getCpf)
                .returns(true, Customer::isActive);
        assertThat(customer.getAuditInfo()).isNotNull();
        assertThat(customer.getAuditInfo().getCreatedAt()).isNotNull();
        assertThat(customer.getAuditInfo().getUpdatedAt()).isNotNull();
        assertThat(customer.getAuditInfo().getCreatedAt()).isEqualTo(customer.getAuditInfo().getUpdatedAt());
    }

    @Test
    void shouldThrowExceptionWhenCustomerNameIsNull() {
        final var email = Email.of("john@example.com");
        final var phoneNumber = PhoneNumber.of("47999999999");
        final var cpf = CPF.of("12345678901");

        final var exception = assertThrows(InvalidCustomerNameException.class,
                () -> Customer.newCustomer(null, email, phoneNumber, cpf));

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invalid customer name: null");
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        final var customerName = CustomerName.of("John Doe");
        final var phoneNumber = PhoneNumber.of("47999999999");
        final var cpf = CPF.of("12345678901");

        final var exception = assertThrows(InvalidEmailException.class,
                () -> Customer.newCustomer(customerName, null, phoneNumber, cpf));

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invalid customer email: null");
    }

    @Test
    void shouldThrowExceptionWhenPhoneNumberIsNull() {
        final var customerName = CustomerName.of("John Doe");
        final var email = Email.of("john@example.com");
        final var cpf = CPF.of("12345678901");

        final var exception = assertThrows(InvalidPhoneNumberException.class,
                () -> Customer.newCustomer(customerName, email, null, cpf));

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invalid customer phone number: null");
    }

    @Test
    void shouldThrowExceptionWhenCPFIsNull() {
        final var customerName = CustomerName.of("John Doe");
        final var email = Email.of("john@example.com");
        final var phoneNumber = PhoneNumber.of("47999999999");

        final var exception = assertThrows(InvalidCPFException.class,
                () -> Customer.newCustomer(customerName, email, phoneNumber, null));

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invalid customer CPF: null");
    }

    @Test
    void shouldActivateReturnsNewInstanceWhenInactive() {
        final var customerId = CustomerId.of(123L);
        final var customerName = CustomerName.of("John Doe");
        final var email = Email.of("john@example.com");
        final var phoneNumber = PhoneNumber.of("47999999999");
        final var cpf = CPF.of("12345678901");
        final var now = Instant.now();
        final var auditInfo = AuditInfo.of(now, now);
        final var customer = Customer.with(customerId, customerName, email, phoneNumber, cpf, false, auditInfo);

        final var activatedCustomer = customer.activate();

        assertThat(activatedCustomer.isActive()).isTrue();
        assertThat(activatedCustomer).isNotNull();
        assertThat(activatedCustomer.getAuditInfo().getCreatedAt()).isEqualTo(customer.getAuditInfo().getCreatedAt());
        assertThat(activatedCustomer.getAuditInfo().getUpdatedAt()).isAfter(customer.getAuditInfo().getUpdatedAt());
    }

    @Test
    void shouldDoNothingWhenActiveACustomerThatIsAlreadyActive() {
        final var customerId = CustomerId.of(123L);
        final var customerName = CustomerName.of("John Doe");
        final var email = Email.of("john@example.com");
        final var phoneNumber = PhoneNumber.of("47999999999");
        final var cpf = CPF.of("12345678901");
        final var now = Instant.now();
        final var auditInfo = AuditInfo.of(now, now);
        final var customer = Customer.with(customerId, customerName, email, phoneNumber, cpf, true, auditInfo);

        final var activatedCustomer = customer.activate();

        assertThat(activatedCustomer.isActive()).isTrue();
        assertThat(activatedCustomer).isNotNull();
        assertThat(activatedCustomer.getAuditInfo().getCreatedAt()).isEqualTo(customer.getAuditInfo().getCreatedAt());
        assertThat(activatedCustomer.getAuditInfo().getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void shouldDoNothingWhenDeactivatingACustomerThatIsAlreadyInactive() {
        final var customerId = CustomerId.of(123L);
        final var customerName = CustomerName.of("John Doe");
        final var email = Email.of("john@example.com");
        final var phoneNumber = PhoneNumber.of("47999999999");
        final var cpf = CPF.of("12345678901");
        final var now = Instant.now();
        final var auditInfo = AuditInfo.of(now, now);
        final var customer = Customer.with(customerId, customerName, email, phoneNumber, cpf, false, auditInfo);

        final var deactivatedCustomer = customer.deactivate();

        assertThat(deactivatedCustomer.isActive()).isFalse();
        assertThat(deactivatedCustomer).isNotNull();
        assertThat(deactivatedCustomer.getAuditInfo().getCreatedAt()).isEqualTo(customer.getAuditInfo().getCreatedAt());
        assertThat(deactivatedCustomer.getAuditInfo().getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void shouldDeactivateReturnsNewInstanceWhenActive() {
        final var customerId = CustomerId.of(123L);
        final var customerName = CustomerName.of("John Doe");
        final var email = Email.of("john@example.com");
        final var phoneNumber = PhoneNumber.of("47999999999");
        final var cpf = CPF.of("12345678901");
        final var now = Instant.now();
        final var auditInfo = AuditInfo.of(now, now);
        final var customer = Customer.with(customerId, customerName, email, phoneNumber, cpf, true, auditInfo);

        final var deactivatedCustomer = customer.deactivate();

        assertThat(deactivatedCustomer.isActive()).isFalse();
        assertThat(deactivatedCustomer).isNotNull();
        assertThat(deactivatedCustomer.getAuditInfo().getCreatedAt()).isEqualTo(customer.getAuditInfo().getCreatedAt());
        assertThat(deactivatedCustomer.getAuditInfo().getUpdatedAt()).isAfter(customer.getAuditInfo().getUpdatedAt());
    }

    @Test
    void shouldUpdateReturnsNewInstanceWithUpdatedData() {
        final var customer = Customer.newCustomer(
                CustomerName.of("John Doe"),
                Email.of("john@example.com"),
                PhoneNumber.of("47999999999"),
                CPF.of("12345678901"));
        final var updatedCustomer = customer.update(
                CustomerName.of("Jane Doe"),
                Email.of("jane@example.com"),
                PhoneNumber.of("47988888888"),
                CPF.of("98765432101"));

        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer.getCustomerName().getValue()).isEqualTo("Jane Doe");
        assertThat(updatedCustomer.getEmail().getValue()).isEqualTo("jane@example.com");
        assertThat(updatedCustomer.getPhoneNumber().getValue()).isEqualTo("47988888888");
        assertThat(updatedCustomer.getCpf().getValue()).isEqualTo("98765432101");
        assertThat(updatedCustomer.getAuditInfo()).isNotNull();
        assertThat(updatedCustomer.getAuditInfo().getCreatedAt()).isEqualTo(customer.getAuditInfo().getCreatedAt());
        assertThat(updatedCustomer.getAuditInfo().getUpdatedAt()).isAfter(customer.getAuditInfo().getUpdatedAt());
    }
}
