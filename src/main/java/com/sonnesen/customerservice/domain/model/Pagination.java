package com.sonnesen.customerservice.domain.model;

import java.util.List;
import java.util.function.Function;

public record Pagination<T>(int currentPage, int perPage, long totalItems, List<T> items) {

    public <R> Pagination<R> mapItems(Function<T, R> mapper) {
        return new Pagination<>(currentPage, perPage, totalItems, items.stream().map(mapper).toList());
    }
}
