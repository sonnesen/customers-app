package com.sonnesen.customerservice.adapters.inbound.graphql.dto;

public record GraphqlCustomerDTO(
        Long customerId,
        String name,
        String email,
        String phoneNumber,
        String cpf,
        Boolean active,
        String createdAt,
        String updatedAt) {
}
