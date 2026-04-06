package com.sonnesen.customerservice.adapters.inbound.rest.mapper;

import java.time.Instant;
import java.time.OffsetDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sonnesen.customerservice.adapters.inbound.rest.model.CreateCustomerRequest;
import com.sonnesen.customerservice.adapters.inbound.rest.model.CreateCustomerResponse;
import com.sonnesen.customerservice.adapters.inbound.rest.model.CustomerResponse;
import com.sonnesen.customerservice.adapters.inbound.rest.model.UpdateCustomerRequest;
import com.sonnesen.customerservice.application.usecase.create.CreateCustomerCommand;
import com.sonnesen.customerservice.application.usecase.create.CreateCustomerOutput;
import com.sonnesen.customerservice.application.usecase.get.CustomerOutput;
import com.sonnesen.customerservice.application.usecase.list.CustomerListOutput;
import com.sonnesen.customerservice.application.usecase.update.UpdateCustomerCommand;
import com.sonnesen.customerservice.domain.model.CPF;
import com.sonnesen.customerservice.domain.model.CustomerId;
import com.sonnesen.customerservice.domain.model.CustomerName;
import com.sonnesen.customerservice.domain.model.Email;
import com.sonnesen.customerservice.domain.model.PhoneNumber;

@Mapper(componentModel = "spring")
public interface CustomerRestMapper {

    default OffsetDateTime mapOffsetDateTime(final Instant instant) {
        if (instant == null) {
            return null;
        }
        return OffsetDateTime.ofInstant(instant, java.time.ZoneOffset.UTC);
    }

    default CustomerId toCustomerId(final Long id) {
        return CustomerId.of(id);
    }

    default CustomerName toCustomerName(final String name) {
        return CustomerName.of(name);
    }

    default Email toEmail(final String email) {
        return Email.of(email);
    }

    default CPF toCPF(final String cpf) {
        return CPF.of(cpf);
    }

    default PhoneNumber toPhoneNumber(final String phoneNumber) {
        return PhoneNumber.of(phoneNumber);
    }

    CreateCustomerResponse toResponse(CreateCustomerOutput customer);

    CustomerResponse toResponse(CustomerOutput customer);

    CustomerResponse toResponse(CustomerListOutput customer);

    CreateCustomerCommand toCommand(CreateCustomerRequest customer);

    @Mapping(source = "id", target = "customerId")
    UpdateCustomerCommand toCommand(Long id, UpdateCustomerRequest customer);
}
