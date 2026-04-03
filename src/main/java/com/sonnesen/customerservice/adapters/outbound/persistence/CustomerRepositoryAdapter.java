package com.sonnesen.customerservice.adapters.outbound.persistence;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.sonnesen.customerservice.adapters.outbound.persistence.mapper.CustomerPersistenceMapper;
import com.sonnesen.customerservice.adapters.outbound.persistence.repository.CustomerJpaRepository;
import com.sonnesen.customerservice.domain.model.Customer;
import com.sonnesen.customerservice.domain.model.Page;
import com.sonnesen.customerservice.domain.model.Pagination;
import com.sonnesen.customerservice.ports.outbound.persistence.repository.CustomerRepositoryPort;

import jakarta.transaction.Transactional;

public class CustomerRepositoryAdapter implements CustomerRepositoryPort {

    private static final String CUSTOMER_ID_MUST_NOT_BE_NULL = "customerId must not be null";

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerPersistenceMapper customerMapper;

    public CustomerRepositoryAdapter(final CustomerJpaRepository customerJpaRepository,
            final CustomerPersistenceMapper customerMapper) {
        this.customerJpaRepository = Objects.requireNonNull(customerJpaRepository,
                "customerJpaRepository must not be null");
        this.customerMapper = Objects.requireNonNull(customerMapper, "customerMapper must not be null");
    }

    @Transactional
    @Override
    public Customer create(final Customer customer) {
        return save(customer);
    }

    @Override
    public boolean existsByEmail(final String email) {
        return customerJpaRepository.existsByEmail(email);
    }

    @Override
    public Optional<Customer> findById(final Long customerId) {
        Objects.requireNonNull(customerId, CUSTOMER_ID_MUST_NOT_BE_NULL);
        return customerJpaRepository.findById(customerId).map(customerMapper::toDomain);
    }

    @Override
    public Pagination<Customer> findAll(final Page page) {
        Objects.requireNonNull(page, "page must not be null");
        final var withPage = Pageable.ofSize(page.perPage()).withPage(page.currentPage());
        final var pageResult = customerJpaRepository.findAll(withPage);
        return new Pagination<>(pageResult.getNumber(), pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(customerMapper::toDomain).toList());
    }

    @Transactional
    @Override
    public Customer update(final Customer customer) {
        return save(customer);
    }

    private Customer save(final Customer customer) {
        Objects.requireNonNull(customer, "customer must not be null");
        final var toPersist = Objects.requireNonNull(customerMapper.toEntity(customer), "toPersist must not be null");
        final var persisted = customerJpaRepository.save(toPersist);
        return customerMapper.toDomain(persisted);
    }

    @Override
    public boolean existsByCpf(final String cpf) {
        return customerJpaRepository.existsByCpf(cpf);
    }

}
