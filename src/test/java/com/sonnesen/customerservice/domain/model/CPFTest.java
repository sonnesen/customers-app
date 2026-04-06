package com.sonnesen.customerservice.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.sonnesen.customerservice.domain.exception.InvalidCPFException;
import com.sonnesen.customerservice.domain.model.CPF;

class CPFTest {

    @Test
    void shouldCreateCPFWithValidValue() {
        final var cpf = CPF.of("12345678901");

        assertThat(cpf.getValue()).isEqualTo("12345678901");
    }

    @Test
    void shouldThrowExceptionForNullCPF() {
        final var exception = assertThrows(InvalidCPFException.class, () -> CPF.of(null));

        assertThat(exception).hasMessageContaining("Invalid CPF: null");
    }

    @Test
    void shouldThrowExceptionForCPFWithNonDigits() {
        final var exception = assertThrows(InvalidCPFException.class, () -> CPF.of("1234567890A"));

        assertThat(exception).hasMessageContaining("Invalid CPF: 1234567890A");
    }

    @Test
    void shouldThrowExceptionForCPFWithLessThan11Digits() {
        final var exception = assertThrows(InvalidCPFException.class, () -> CPF.of("1234567890"));

        assertThat(exception).hasMessageContaining("Invalid CPF: 1234567890");
    }

    @Test
    void shouldThrowExceptionForCPFWithMoreThan11Digits() {
        final var exception = assertThrows(InvalidCPFException.class, () -> CPF.of("123456789012"));

        assertThat(exception).hasMessageContaining("Invalid CPF: 123456789012");
    }

    @Test
    void shouldNotBeEqualToDifferentType() {
        final var cpf = CPF.of("12345678901");
        final var other = "12345678901";

        assertThat(cpf).isNotEqualTo(other);
    }

    @Test
    void shouldBeEqualToItself() {
        final var cpf = CPF.of("12345678901");

        assertThat(cpf).isEqualTo(cpf);
    }

    @Test
    void shouldBeEqualForSameValue() {
        final var cpf1 = CPF.of("12345678901");
        final var cpf2 = CPF.of("12345678901");

        assertThat(cpf1).isEqualTo(cpf2);
    }

}