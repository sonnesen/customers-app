package com.sonnesen.customerservice.adapters.outbound.persistence.mapper;

import java.time.Instant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sonnesen.customerservice.adapters.outbound.persistence.entity.CustomerJpaEntity;
import com.sonnesen.customerservice.domain.model.AuditInfo;
import com.sonnesen.customerservice.domain.model.CPF;
import com.sonnesen.customerservice.domain.model.Customer;
import com.sonnesen.customerservice.domain.model.CustomerId;
import com.sonnesen.customerservice.domain.model.Email;
import com.sonnesen.customerservice.domain.model.PhoneNumber;

@Mapper(componentModel = "spring")
public interface CustomerPersistenceMapper {

    default Customer toDomain(CustomerJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return Customer.with(
                toCustomerId(entity.getId()),
                entity.getName(),
                toEmail(entity.getEmail()),
                toPhoneNumber(entity.getPhoneNumber()),
                toCpf(entity.getCpf()),
                entity.isActive(),
                toAuditInfo(entity.getCreatedAt(), entity.getUpdatedAt()));
    }

    @Mapping(target = "id", expression = "java(customer.getId() != null ? customer.getId().getValue() : null)")
    @Mapping(target = "email", expression = "java(customer.getEmail().getValue())")
    @Mapping(target = "phoneNumber", expression = "java(customer.getPhoneNumber().getValue())")
    @Mapping(target = "cpf", expression = "java(customer.getCpf().getValue())")
    @Mapping(target = "createdAt", expression = "java(customer.getAuditInfo() != null ? customer.getAuditInfo().getCreatedAt() : null)")
    @Mapping(target = "updatedAt", expression = "java(customer.getAuditInfo() != null ? customer.getAuditInfo().getUpdatedAt() : null)")
    CustomerJpaEntity toEntity(Customer customer);

    default CustomerId toCustomerId(Long id) {
        return id == null ? null : CustomerId.of(id);
    }

    default Email toEmail(String value) {
        return value == null ? null : Email.of(value);
    }

    default PhoneNumber toPhoneNumber(String value) {
        return value == null ? null : PhoneNumber.of(value);
    }

    default CPF toCpf(String value) {
        return value == null ? null : CPF.of(value);
    }

    default AuditInfo toAuditInfo(Instant createdAt, Instant updatedAt) {
        return AuditInfo.of(createdAt, updatedAt);
    }

}
