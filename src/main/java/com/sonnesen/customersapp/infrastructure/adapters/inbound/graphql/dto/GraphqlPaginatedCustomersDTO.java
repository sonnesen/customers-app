package com.sonnesen.customersapp.infrastructure.adapters.inbound.graphql.dto;

import java.util.List;

public record GraphqlPaginatedCustomersDTO(
    List<GraphqlCustomerDTO> items,
    long totalItems,
    int page,
    int perPage
) {
}
