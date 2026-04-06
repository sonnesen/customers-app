package com.sonnesen.customerservice.ports.outbound.persistence.repository;

import java.util.Optional;

import com.sonnesen.customerservice.domain.model.CPF;
import com.sonnesen.customerservice.domain.model.Customer;
import com.sonnesen.customerservice.domain.model.CustomerId;
import com.sonnesen.customerservice.domain.model.Email;
import com.sonnesen.customerservice.domain.model.Page;
import com.sonnesen.customerservice.domain.model.Pagination;

public interface CustomerRepositoryPort {

    Customer create(Customer newCustomer);

    boolean existsByEmail(Email email);

    Optional<Customer> findById(CustomerId customerId);

    Pagination<Customer> findAll(Page page);

    Customer update(Customer customer);

    boolean existsByCpf(CPF cpf);

}
