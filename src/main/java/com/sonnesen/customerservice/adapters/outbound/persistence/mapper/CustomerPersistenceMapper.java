package com.sonnesen.customerservice.adapters.outbound.persistence.mapper;

import java.time.Instant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sonnesen.customerservice.adapters.outbound.persistence.entity.CustomerJpaEntity;
import com.sonnesen.customerservice.domain.model.AuditInfo;
import com.sonnesen.customerservice.domain.model.CPF;
import com.sonnesen.customerservice.domain.model.Customer;
import com.sonnesen.customerservice.domain.model.CustomerId;
import com.sonnesen.customerservice.domain.model.CustomerName;
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
                toCustomerName(entity.getName()),
                toEmail(entity.getEmail()),
                toPhoneNumber(entity.getPhoneNumber()),
                toCpf(entity.getCpf()),
                entity.isActive(),
                toAuditInfo(entity.getCreatedAt(), entity.getUpdatedAt()));
    }

    @Mapping(target = "id", expression = "java(customer.getId() != null ? customer.getId().getValue() : null)")
    @Mapping(target = "name", expression = "java(customer.getCustomerName() != null ? customer.getCustomerName().getValue() : null)")
    @Mapping(target = "email", expression = "java(customer.getEmail() != null ? customer.getEmail().getValue() : null)")
    @Mapping(target = "phoneNumber", expression = "java(customer.getPhoneNumber() != null ? customer.getPhoneNumber().getValue() : null)")
    @Mapping(target = "cpf", expression = "java(customer.getCpf() != null ? customer.getCpf().getValue() : null)")
    @Mapping(target = "createdAt", expression = "java(customer.getAuditInfo() != null ? customer.getAuditInfo().getCreatedAt() : null)")
    @Mapping(target = "updatedAt", expression = "java(customer.getAuditInfo() != null ? customer.getAuditInfo().getUpdatedAt() : null)")
    CustomerJpaEntity toEntity(Customer customer);

    default CustomerId toCustomerId(Long id) {
        return id == null ? null : CustomerId.of(id);
    }

    default CustomerName toCustomerName(String name) {
        return name == null ? null : CustomerName.of(name);
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
