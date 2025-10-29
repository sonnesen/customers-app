package com.sonnesen.customersapp.infrastructure.adapters.inbound.rest;

import java.net.URI;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.sonnesen.customers.api.CustomersApi;
import com.sonnesen.customers.model.CreateCustomerDTO;
import com.sonnesen.customers.model.CustomerDTO;
import com.sonnesen.customers.model.PaginatedCustomersDTO;
import com.sonnesen.customers.model.UpdateCustomerDTO;
import com.sonnesen.customersapp.application.domain.pagination.Page;
import com.sonnesen.customersapp.application.domain.pagination.Pagination;
import com.sonnesen.customersapp.application.ports.inbound.create.ForCreatingCustomer;
import com.sonnesen.customersapp.application.ports.inbound.delete.ForDeletingCustomerById;
import com.sonnesen.customersapp.application.ports.inbound.get.ForGettingCustomerById;
import com.sonnesen.customersapp.application.ports.inbound.list.ForListingCustomers;
import com.sonnesen.customersapp.application.ports.inbound.update.ForUpdatingCustomer;
import com.sonnesen.customersapp.infrastructure.adapters.inbound.mapper.CustomerMapper;

@RestController
public class CustomerController implements CustomersApi {

    private final ForCreatingCustomer creatingCustomerPort;
    private final ForListingCustomers listingCustomersPort;
    private final ForGettingCustomerById gettingCustomerByIdPort;
    private final ForDeletingCustomerById deletingCustomerByIdPort;
    private final ForUpdatingCustomer updatingCustomerPort;
    private final CustomerMapper customerMapper;

    public CustomerController(
            ForCreatingCustomer creatingCustomerPort,
            ForListingCustomers listingCustomersPort,
            ForGettingCustomerById gettingCustomerByIdPort,
            ForDeletingCustomerById deletingCustomerByIdPort,
            ForUpdatingCustomer updatingCustomerPort,
            CustomerMapper customerMapper) {
        this.creatingCustomerPort = Objects.requireNonNull(creatingCustomerPort);
        this.listingCustomersPort = Objects.requireNonNull(listingCustomersPort);
        this.gettingCustomerByIdPort = Objects.requireNonNull(gettingCustomerByIdPort);
        this.deletingCustomerByIdPort = Objects.requireNonNull(deletingCustomerByIdPort);
        this.updatingCustomerPort = Objects.requireNonNull(updatingCustomerPort);
        this.customerMapper = Objects.requireNonNull(customerMapper);
    }

    @Override
    public ResponseEntity<CustomerDTO> createCustomer(final CreateCustomerDTO body) {
        final var useCaseInput = customerMapper.fromDTO(body);
        final var useCaseOutput = creatingCustomerPort.create(useCaseInput);
        final var uri = URI.create("/customers/" + useCaseOutput.id());
        return ResponseEntity.created(uri).body(customerMapper.toDTO(useCaseOutput));
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(Long customerId) {
        deletingCustomerByIdPort.deleteById(customerId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CustomerDTO> updateCustomer(Long customerId, final UpdateCustomerDTO body) {
        final var input = customerMapper.fromDTO(customerId, body);
        final var output = updatingCustomerPort.update(input);
        return ResponseEntity.ok(customerMapper.toDTO(output));
    }

    @Override
    public ResponseEntity<PaginatedCustomersDTO> getAllCustomers(Integer page, Integer perPage) {
        Pagination<CustomerDTO> customers = listingCustomersPort.list(new Page(page, perPage))
                                                                .mapItems(customerMapper::toDTO);

        PaginatedCustomersDTO paginatedCustomers = new PaginatedCustomersDTO()
            .items(customers.items())
            .page(customers.currentPage())
            .perPage(customers.perPage())
            .totalItems(customers.totalItems());

        return ResponseEntity.ok(paginatedCustomers);
    }

    @Override
    public ResponseEntity<CustomerDTO> getCustomerById(Long id) {
        final var output = gettingCustomerByIdPort.getById(id);
        return ResponseEntity.ok(customerMapper.toDTO(output));
    }

}
