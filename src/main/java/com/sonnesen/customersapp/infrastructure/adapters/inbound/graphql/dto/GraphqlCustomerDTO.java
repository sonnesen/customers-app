package com.sonnesen.customersapp.infrastructure.adapters.inbound.graphql.dto;

public record GraphqlCustomerDTO(
    Long id,
    String name,
    String email,
    String phoneNumber,
    String cpf,
    Boolean active,
    String createdAt,
    String updatedAt,
    String deletedAt
) {
}
