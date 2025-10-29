package com.sonnesen.customersapp.application.ports.outbound.repository;

import java.util.Optional;
import com.sonnesen.customersapp.application.domain.customer.Customer;
import com.sonnesen.customersapp.application.domain.pagination.Page;
import com.sonnesen.customersapp.application.domain.pagination.Pagination;

public interface CustomerRepository {
    Customer create(Customer customer);
    Customer update(Customer customer);
    Optional<Customer> findById(Long id);
    Pagination<Customer> list(Page page);
    void deleteById(Long id);
}
