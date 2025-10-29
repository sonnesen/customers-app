package com.sonnesen.customersapp.infrastructure.adapters.inbound.mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.sonnesen.customers.model.CreateCustomerDTO;
import com.sonnesen.customers.model.CustomerDTO;
import com.sonnesen.customers.model.UpdateCustomerDTO;
import com.sonnesen.customersapp.application.domain.customer.Customer;
import com.sonnesen.customersapp.application.ports.inbound.create.CreateCustomerInput;
import com.sonnesen.customersapp.application.ports.inbound.create.CreateCustomerOutput;
import com.sonnesen.customersapp.application.ports.inbound.get.GetCustomerByIdOutput;
import com.sonnesen.customersapp.application.ports.inbound.list.ListCustomerOutput;
import com.sonnesen.customersapp.application.ports.inbound.update.UpdateCustomerInput;
import com.sonnesen.customersapp.application.ports.inbound.update.UpdateCustomerOutput;
import com.sonnesen.customersapp.infrastructure.adapters.inbound.graphql.dto.GraphqlCustomerDTO;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "createdAt", expression = "java(mapOffsetDateTime(customer.getCreatedAt()))")
    @Mapping(target = "deletedAt", expression = "java(mapOffsetDateTime(customer.getDeletedAt()))")
    @Mapping(target = "updatedAt", expression = "java(mapOffsetDateTime(customer.getUpdatedAt()))")
    CustomerDTO toDTO(Customer customer);

    @Mapping(target = "createdAt", expression = "java(mapOffsetDateTime(output.createdAt()))")
    @Mapping(target = "deletedAt", expression = "java(mapOffsetDateTime(output.deletedAt()))")
    @Mapping(target = "updatedAt", expression = "java(mapOffsetDateTime(output.updatedAt()))")
    CustomerDTO toDTO(CreateCustomerOutput output);

    CustomerDTO toDTO(ListCustomerOutput output);

    List<CustomerDTO> toDTO(List<ListCustomerOutput> output);

    CustomerDTO toDTO(GetCustomerByIdOutput output);

    CustomerDTO toDTO(UpdateCustomerOutput output);

    CreateCustomerInput fromDTO(CreateCustomerDTO dto);

    UpdateCustomerInput fromDTO(Long id, UpdateCustomerDTO dto);

    default OffsetDateTime mapOffsetDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return OffsetDateTime.ofInstant(instant, java.time.ZoneOffset.UTC);
    }

    GraphqlCustomerDTO toGraphqlDTO(GetCustomerByIdOutput output);

    GraphqlCustomerDTO toGraphqlDTO(ListCustomerOutput output);
    List<GraphqlCustomerDTO> toGraphqlDTO(List<ListCustomerOutput> output);
}
