package com.sonnesen.customersapp.infrastructure.adapters.outbound.persistence;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.sonnesen.customersapp.application.domain.customer.Customer;
import com.sonnesen.customersapp.application.domain.pagination.Page;
import com.sonnesen.customersapp.application.domain.pagination.Pagination;
import com.sonnesen.customersapp.application.ports.outbound.repository.CustomerRepository;
import com.sonnesen.customersapp.infrastructure.adapters.outbound.persistence.entity.CustomerJpaEntity;
import com.sonnesen.customersapp.infrastructure.adapters.outbound.persistence.repository.CustomerJpaRepository;

@Component
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;

    public CustomerRepositoryAdapter(CustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = customerJpaRepository;
    }

    @Override
    public Customer create(Customer customer) {
        return save(customer);
    }

    @Override
    public void deleteById(Long id) {
        customerJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerJpaRepository.findById(id).map(CustomerJpaEntity::toDomain);
    }

    @Override
    public Pagination<Customer> list(Page page) {
        var withPage = Pageable.ofSize(page.perPage()).withPage(page.currentPage());
        var pageResult = customerJpaRepository.findAll(withPage);
        var pagination = new Pagination<>(pageResult.getNumber(), pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CustomerJpaEntity::toDomain).toList());
        return pagination;
    }

    @Override
    public Customer update(Customer customer) {
        return save(customer);
    }

    private Customer save(Customer customer) {
        return customerJpaRepository.save(CustomerJpaEntity.of(customer)).toDomain();
    }

}
