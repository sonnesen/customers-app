package com.sonnesen.customerservice.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.sonnesen.customerservice.domain.model.Page;

class PageTest {

    @Test
    void shouldCreatePageWithValidInput() {
        final var page = new Page(0, 10);

        assertThat(page.currentPage()).isZero();
        assertThat(page.perPage()).isEqualTo(10);
    }

    @Test
    void shouldThrowExceptionForNegativeCurrentPage() {
        final var exception = org.junit.jupiter.api.Assertions.assertThrows(
                com.sonnesen.customerservice.domain.exception.InvalidPageException.class, () -> {
                    new Page(-1, 10);
                });
        assertThat(exception).hasMessage("Invalid page value: -1. It must be greater than or equal to 0");
    }

    @Test
    void shouldThrowExceptionForZeroPerPage() {
        final var exception = org.junit.jupiter.api.Assertions.assertThrows(
                com.sonnesen.customerservice.domain.exception.InvalidPageException.class, () -> {
                    new Page(0, 0);
                });
        assertThat(exception).hasMessage("Invalid per page value: 0. It must be greater than 0");
    }
}
