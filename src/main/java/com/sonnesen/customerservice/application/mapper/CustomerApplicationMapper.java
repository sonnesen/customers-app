package com.sonnesen.customerservice.application.mapper;

import java.time.Instant;
import java.time.OffsetDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sonnesen.customerservice.adapters.inbound.graphql.dto.GraphqlCustomerDTO;
import com.sonnesen.customerservice.adapters.inbound.rest.model.CreateCustomerRequest;
import com.sonnesen.customerservice.adapters.inbound.rest.model.CreateCustomerResponse;
import com.sonnesen.customerservice.adapters.inbound.rest.model.CustomerResponse;
import com.sonnesen.customerservice.adapters.inbound.rest.model.UpdateCustomerRequest;
import com.sonnesen.customerservice.application.usecase.create.CreateCustomerCommand;
import com.sonnesen.customerservice.application.usecase.create.CreateCustomerOutput;
import com.sonnesen.customerservice.application.usecase.get.CustomerOutput;
import com.sonnesen.customerservice.application.usecase.list.CustomerListOutput;
import com.sonnesen.customerservice.application.usecase.update.UpdateCustomerCommand;

@Mapper(componentModel = "spring")
public interface CustomerApplicationMapper {

    default OffsetDateTime mapOffsetDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return OffsetDateTime.ofInstant(instant, java.time.ZoneOffset.UTC);
    }

    @Mapping(target = "customerId", expression = "java(customer.customerId())")
    CreateCustomerResponse toResponse(CreateCustomerOutput customer);

    @Mapping(target = "customerId", expression = "java(customer.customerId())")
    @Mapping(target = "email", expression = "java(customer.email())")
    @Mapping(target = "phoneNumber", expression = "java(customer.phoneNumber())")
    @Mapping(target = "cpf", expression = "java(customer.cpf())")
    @Mapping(target = "createdAt", expression = "java(customer.createdAt() != null ? mapOffsetDateTime(customer.createdAt()) : null)")
    @Mapping(target = "updatedAt", expression = "java(customer.updatedAt() != null ? mapOffsetDateTime(customer.updatedAt()) : null)")
    CustomerResponse toResponse(CustomerOutput customer);

    @Mapping(target = "customerId", expression = "java(customer.customerId())")
    @Mapping(target = "email", expression = "java(customer.email())")
    @Mapping(target = "phoneNumber", expression = "java(customer.phoneNumber())")
    @Mapping(target = "cpf", expression = "java(customer.cpf())")
    @Mapping(target = "createdAt", expression = "java(customer.createdAt() != null ? mapOffsetDateTime(customer.createdAt()) : null)")
    @Mapping(target = "updatedAt", expression = "java(customer.updatedAt() != null ? mapOffsetDateTime(customer.updatedAt()) : null)")
    CustomerResponse toResponse(CustomerListOutput customer);

    @Mapping(target = "email", expression = "java(Email.of(customer.getEmail()))")
    @Mapping(target = "phoneNumber", expression = "java(PhoneNumber.of(customer.getPhoneNumber()))")
    @Mapping(target = "cpf", expression = "java(CPF.of(customer.getCpf()))")
    CreateCustomerCommand toCommand(CreateCustomerRequest customer);

    @Mapping(target = "customerId", expression = "java(CustomerId.of(id))")
    @Mapping(target = "email", expression = "java(Email.of(customer.getEmail()))")
    @Mapping(target = "phoneNumber", expression = "java(PhoneNumber.of(customer.getPhoneNumber()))")
    @Mapping(target = "cpf", expression = "java(CPF.of(customer.getCpf()))")
    UpdateCustomerCommand toCommand(Long id, UpdateCustomerRequest customer);

    @Mapping(target = "customerId", expression = "java(customer.customerId())")
    @Mapping(target = "email", expression = "java(customer.email())")
    @Mapping(target = "phoneNumber", expression = "java(customer.phoneNumber())")
    @Mapping(target = "cpf", expression = "java(customer.cpf())")
    @Mapping(target = "createdAt", expression = "java(customer.createdAt() != null ? mapOffsetDateTime(customer.createdAt()).toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(customer.updatedAt() != null ? mapOffsetDateTime(customer.updatedAt()).toString() : null)")
    GraphqlCustomerDTO toGraphqlDTO(CustomerOutput customer);

    @Mapping(target = "customerId", expression = "java(customer.customerId())")
    @Mapping(target = "email", expression = "java(customer.email())")
    @Mapping(target = "phoneNumber", expression = "java(customer.phoneNumber())")
    @Mapping(target = "cpf", expression = "java(customer.cpf())")
    @Mapping(target = "createdAt", expression = "java(customer.createdAt() != null ? mapOffsetDateTime(customer.createdAt()).toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(customer.updatedAt() != null ? mapOffsetDateTime(customer.updatedAt()).toString() : null)")
    GraphqlCustomerDTO toGraphqlDTO(CustomerListOutput customer);
}
