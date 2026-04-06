package com.sonnesen.customerservice.domain.model;

import com.sonnesen.customerservice.domain.exception.InvalidPageException;

public record Page(int currentPage, int perPage) {

    public Page {
        if (currentPage < 0) {
            throw new InvalidPageException(
                    "Invalid page value: " + currentPage + ". It must be greater than or equal to 0");
        }
        if (perPage < 1) {
            throw new InvalidPageException(
                    "Invalid per page value: " + perPage + ". It must be greater than 0");
        }
    }
}
