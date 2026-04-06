package com.sonnesen.customerservice.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.sonnesen.customerservice.domain.exception.InvalidCustomerIdException;

class CustomerIdTest {

    @Test
    void shouldCreateCustomerIdWithValidValue() {
        final var customerId = CustomerId.of(1L);

        assertThat(customerId.getValue()).isEqualTo(1L);
    }

    @Test
    void shouldThrowExceptionForNullCustomerId() {
        final var exception = assertThrows(InvalidCustomerIdException.class, () -> CustomerId.of(null));

        assertThat(exception).hasMessageContaining("Invalid customer ID: null");
    }

    @Test
    void shouldThrowExceptionForNegativeCustomerId() {
        final var exception = assertThrows(InvalidCustomerIdException.class, () -> CustomerId.of(-1L));

        assertThat(exception).hasMessageContaining("Invalid customer ID: -1");
    }

    @Test
    void shouldThrowExceptionForZeroCustomerId() {
        final var exception = assertThrows(InvalidCustomerIdException.class, () -> CustomerId.of(0L));

        assertThat(exception).hasMessageContaining("Invalid customer ID: 0");
    }

    @Test
    void shouldBeEqualForSameValue() {
        final var customerId1 = CustomerId.of(1L);
        final var customerId2 = CustomerId.of(1L);

        assertThat(customerId1).isEqualTo(customerId2);
    }

    @Test
    void shouldNotBeEqualForDifferentValues() {
        final var customerId1 = CustomerId.of(1L);
        final var customerId2 = CustomerId.of(2L);

        assertThat(customerId1).isNotEqualTo(customerId2);
    }

    @Test
    void shouldHaveSameHashCodeForSameValue() {
        final var customerId1 = CustomerId.of(1L);
        final var customerId2 = CustomerId.of(1L);

        assertThat(customerId1).hasSameHashCodeAs(customerId2);
    }

    @Test
    void shouldNotHaveSameHashCodeForDifferentValues() {
        final var customerId1 = CustomerId.of(1L);
        final var customerId2 = CustomerId.of(2L);

        assertThat(customerId1).doesNotHaveSameHashCodeAs(customerId2);
    }

    @Test
    void shouldNotBeEqualToNull() {
        final var customerId = CustomerId.of(1L);

        assertThat(customerId).isNotEqualTo(null);
    }

    @Test
    void shouldNotBeEqualToDifferentType() {
        final var customerId = CustomerId.of(1L);
        final var otherObject = new Object();

        assertThat(customerId).isNotEqualTo(otherObject);
    }

    @Test
    void shouldBeEqualToItself() {
        final var customerId = CustomerId.of(1L);

        assertThat(customerId).isEqualTo(customerId);
    }
}