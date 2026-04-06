package com.sonnesen.customerservice.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.sonnesen.customerservice.domain.exception.InvalidPhoneNumberException;

class PhoneNumberTest {

    @ParameterizedTest
    @ValueSource(strings = { "9999999999", "88888888888" })
    void shouldCreatePhoneNumberWithValidInput(final String input) {
        final var phoneNumber = PhoneNumber.of(input);

        assertThat(phoneNumber.getValue()).isEqualTo(input);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "123456789", "123456789012", "abcdefghij", "12345abcde" })
    void shouldThrowExceptionForInvalidPhoneNumber(final String input) {
        final var exception = assertThrows(InvalidPhoneNumberException.class, () -> {
            PhoneNumber.of(input);
        });
        assertThat(exception).isInstanceOf(InvalidPhoneNumberException.class)
                .hasMessage("Invalid phone number: " + input + ". It should be a string of 10 or 11 digits");
    }

    @Test
    void shouldConsiderTwoPhoneNumbersWithSameValueAsEqual() {
        final var phoneNumber1 = PhoneNumber.of("9999999999");
        final var phoneNumber2 = PhoneNumber.of("9999999999");

        assertThat(phoneNumber1).isEqualTo(phoneNumber2);
        assertThat(phoneNumber1.hashCode()).hasSameHashCodeAs(phoneNumber2.hashCode());
    }

    @Test
    void shouldConsiderTwoPhoneNumbersWithDifferentValuesAsNotEqual() {
        final var phoneNumber1 = PhoneNumber.of("9999999999");
        final var phoneNumber2 = PhoneNumber.of("88888888888");

        assertThat(phoneNumber1).isNotEqualTo(phoneNumber2);
    }

    @Test
    void shouldBeEqualToItself() {
        final var phoneNumber = PhoneNumber.of("9999999999");

        assertThat(phoneNumber).isEqualTo(phoneNumber);
    }

    @Test
    void shouldNotBeEqualToDifferentType() {
        final var phoneNumber = PhoneNumber.of("9999999999");
        final var other = "9999999999";

        assertThat(phoneNumber).isNotEqualTo(other);
    }

}
