package com.sonnesen.customerservice.adapters.inbound.rest;

import java.net.URI;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.sonnesen.customerservice.adapters.inbound.rest.model.CreateCustomerRequest;
import com.sonnesen.customerservice.adapters.inbound.rest.model.CreateCustomerResponse;
import com.sonnesen.customerservice.adapters.inbound.rest.model.CustomerResponse;
import com.sonnesen.customerservice.adapters.inbound.rest.model.PaginatedCustomersResponse;
import com.sonnesen.customerservice.adapters.inbound.rest.model.UpdateCustomerRequest;
import com.sonnesen.customerservice.application.mapper.CustomerApplicationMapper;
import com.sonnesen.customerservice.application.usecase.activate.ActivateCustomerUseCase;
import com.sonnesen.customerservice.application.usecase.create.CreateCustomerUseCase;
import com.sonnesen.customerservice.application.usecase.deactivate.DeactivateCustomerUseCase;
import com.sonnesen.customerservice.application.usecase.get.GetCustomerByIdUseCase;
import com.sonnesen.customerservice.application.usecase.list.ListCustomersUseCase;
import com.sonnesen.customerservice.application.usecase.update.UpdateCustomerUseCase;
import com.sonnesen.customerservice.domain.model.Page;

@RestController
public class CustomerController implements CustomersApi {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final ActivateCustomerUseCase activateCustomerUseCase;
    private final DeactivateCustomerUseCase deactivateCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final ListCustomersUseCase listCustomersUseCase;
    private final GetCustomerByIdUseCase getCustomerByIdUseCase;
    private final CustomerApplicationMapper customerMapper;

    public CustomerController(final CreateCustomerUseCase createCustomerUseCase,
            final ActivateCustomerUseCase activateCustomerUseCase,
            final DeactivateCustomerUseCase deactivateCustomerUseCase,
            final UpdateCustomerUseCase updateCustomerUseCase,
            final ListCustomersUseCase listCustomersUseCase,
            final GetCustomerByIdUseCase getCustomerByIdUseCase,
            final CustomerApplicationMapper customerMapper) {
        this.createCustomerUseCase = Objects.requireNonNull(createCustomerUseCase,
                "createCustomerUseCase must not be null");
        this.activateCustomerUseCase = Objects.requireNonNull(activateCustomerUseCase,
                "activateCustomerUseCase must not be null");
        this.deactivateCustomerUseCase = Objects.requireNonNull(deactivateCustomerUseCase,
                "deactivateCustomerUseCase must not be null");
        this.updateCustomerUseCase = Objects.requireNonNull(updateCustomerUseCase,
                "updateCustomerUseCase must not be null");
        this.listCustomersUseCase = Objects.requireNonNull(listCustomersUseCase,
                "listCustomersUseCase must not be null");
        this.getCustomerByIdUseCase = Objects.requireNonNull(getCustomerByIdUseCase,
                "getCustomerByIdUseCase must not be null");
        this.customerMapper = Objects.requireNonNull(customerMapper, "customerMapper must not be null");
    }

    @Override
    public ResponseEntity<CreateCustomerResponse> createCustomer(final CreateCustomerRequest body) {
        final var command = customerMapper.toCommand(body);
        final var output = createCustomerUseCase.execute(command);
        final var uri = Objects.requireNonNull(URI.create("/customers/" + output.customerId()));
        return ResponseEntity.created(uri).body(customerMapper.toResponse(output));
    }

    @Override
    public ResponseEntity<Void> activateCustomer(final Long customerId) {
        activateCustomerUseCase.execute(customerId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deactivateCustomer(final Long customerId) {
        deactivateCustomerUseCase.execute(customerId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateCustomer(final Long customerId, final UpdateCustomerRequest body) {
        final var command = customerMapper.toCommand(customerId, body);
        updateCustomerUseCase.execute(command);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<PaginatedCustomersResponse> getAllCustomers(final Integer page, final Integer perPage) {
        final var customers = listCustomersUseCase.execute(new Page(page, perPage))
                .mapItems(customerMapper::toResponse);

        PaginatedCustomersResponse paginatedCustomers = new PaginatedCustomersResponse()
                .items(customers.items())
                .page(customers.currentPage())
                .perPage(customers.perPage())
                .totalItems(customers.totalItems());

        return ResponseEntity.ok(paginatedCustomers);
    }

    @Override
    public ResponseEntity<CustomerResponse> getCustomerById(final Long id) {
        final var output = getCustomerByIdUseCase.execute(id);
        return ResponseEntity.ok(customerMapper.toResponse(output));
    }

}
