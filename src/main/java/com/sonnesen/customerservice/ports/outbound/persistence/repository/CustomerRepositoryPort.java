package com.sonnesen.customerservice.ports.outbound.persistence.repository;

import java.util.Optional;

import com.sonnesen.customerservice.domain.model.Customer;
import com.sonnesen.customerservice.domain.model.Page;
import com.sonnesen.customerservice.domain.model.Pagination;

public interface CustomerRepositoryPort {

    Customer create(Customer newCustomer);

    boolean existsByEmail(String email);

    Optional<Customer> findById(Long customerId);

    Pagination<Customer> findAll(Page page);

    Customer update(Customer customer);

    boolean existsByCpf(String cpf);

}
