package com.sonnesen.customerservice.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.sonnesen.customerservice.domain.exception.InvalidEmailException;
import com.sonnesen.customerservice.domain.model.Email;

class EmailTest {

    @Test
    void shouldCreateEmail() {
        final var email = Email.of("john.doe@mail.com");

        assertThat(email).isNotNull();
        assertThat(email.getValue()).isEqualTo("john.doe@mail.com");
    }

    @Test
    void shouldBeEqualForSameValue() {
        final var email1 = Email.of("john.doe@mail.com");
        final var email2 = Email.of("john.doe@mail.com");

        assertThat(email1).isEqualTo(email2);
    }

    @Test
    void shouldNotBeEqualForDifferentValues() {
        final var email1 = Email.of("john.doe@mail.com");
        final var email2 = Email.of("jane.doe@mail.com");

        assertThat(email1).isNotEqualTo(email2);
    }

    @Test
    void shouldHaveSameHashCodeForSameValue() {
        final var email1 = Email.of("john.doe@mail.com");
        final var email2 = Email.of("john.doe@mail.com");

        assertThat(email1).hasSameHashCodeAs(email2);
    }

    @ParameterizedTest
    @ValueSource(strings = { "invalid-email", "john.doe.mail.com", "john.doe@", " " })
    @NullAndEmptySource
    void shouldThrowExceptionWhenEmailIsInvalid(final String input) {
        final var exception = assertThrows(InvalidEmailException.class, () -> Email.of(input));

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invalid email: " + input);
    }

    @Test
    void shouldBeEqualToItself() {
        final var email = Email.of("john.doe@mail.com");

        assertThat(email).isEqualTo(email);
    }

    @Test
    void shouldNotBeEqualToDifferentType() {
        final var email = Email.of("john.doe@mail.com");
        final var other = "john.doe@mail.com";

        assertThat(email).isNotEqualTo(other);
    }

}
