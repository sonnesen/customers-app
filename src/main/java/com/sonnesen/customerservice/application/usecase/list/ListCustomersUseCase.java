package com.sonnesen.customerservice.application.usecase.list;

import java.util.Objects;

import com.sonnesen.customerservice.domain.model.Page;
import com.sonnesen.customerservice.domain.model.Pagination;
import com.sonnesen.customerservice.ports.outbound.persistence.repository.CustomerRepositoryPort;

public class ListCustomersUseCase {

    public final CustomerRepositoryPort customerRepository;

    public ListCustomersUseCase(final CustomerRepositoryPort customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository, "customerRepository must not be null");
    }

    public Pagination<CustomerListOutput> execute(final Page page) {
        Objects.requireNonNull(page, "page must not be null");
        return customerRepository.findAll(page).mapItems(CustomerListOutput::from);
    }
}
