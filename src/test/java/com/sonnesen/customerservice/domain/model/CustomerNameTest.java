package com.sonnesen.customerservice.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.sonnesen.customerservice.domain.exception.InvalidCustomerNameException;

class CustomerNameTest {

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        final var exception = assertThrows(InvalidCustomerNameException.class, () -> CustomerName.of(null));

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Customer name cannot be null or blank");
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        final var exception = assertThrows(InvalidCustomerNameException.class, () -> CustomerName.of(""));

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Customer name cannot be null or blank");
    }

    @Test
    void shouldCreateCustomerName() {
        final var name = CustomerName.of("John Doe");

        assertThat(name).isNotNull();
        assertThat(name.getValue()).isEqualTo("John Doe");
    }

    @Test
    void shouldBeEqualForSameValue() {
        final var name1 = CustomerName.of("John Doe");
        final var name2 = CustomerName.of("John Doe");

        assertThat(name1).isEqualTo(name2);
    }

    @Test
    void shouldNotBeEqualForDifferentValues() {
        final var name1 = CustomerName.of("John Doe");
        final var name2 = CustomerName.of("Jane Doe");

        assertThat(name1).isNotEqualTo(name2);
    }

    @Test
    void shouldHaveSameHashCodeForSameValue() {
        final var name1 = CustomerName.of("John Doe");
        final var name2 = CustomerName.of("John Doe");

        assertThat(name1).hasSameHashCodeAs(name2);
    }

    @Test
    void shouldNotHaveSameHashCodeForDifferentValues() {
        final var name1 = CustomerName.of("John Doe");
        final var name2 = CustomerName.of("Jane Doe");

        assertThat(name1).doesNotHaveSameHashCodeAs(name2);
    }

    @Test
    void shouldTrimName() {
        final var name = CustomerName.of("  John Doe  ");

        assertThat(name).isNotNull();
        assertThat(name.getValue()).isEqualTo("John Doe");
    }

    @Test
    void shouldBeEqualWhenTrimmed() {
        final var name1 = CustomerName.of("  John Doe  ");
        final var name2 = CustomerName.of("John Doe");

        assertThat(name1).isEqualTo(name2);
    }

    @Test
    void shouldHaveSameHashCodeWhenTrimmed() {
        final var name1 = CustomerName.of("  John Doe  ");
        final var name2 = CustomerName.of("John Doe");

        assertThat(name1).hasSameHashCodeAs(name2);
    }

    @Test
    void shouldNotBeEqualToNull() {
        final var name = CustomerName.of("John Doe");

        assertThat(name).isNotEqualTo(null);
    }

    @Test
    void shouldNotBeEqualToDifferentType() {
        final var name = CustomerName.of("John Doe");
        final var other = "John Doe";

        assertThat(name).isNotEqualTo(other);
    }

    @Test
    void shouldBeEqualToItself() {
        final var name = CustomerName.of("John Doe");

        assertThat(name).isEqualTo(name);
    }
}
