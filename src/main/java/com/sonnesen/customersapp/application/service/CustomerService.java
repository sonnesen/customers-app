package com.sonnesen.customersapp.application.service;

import org.springframework.stereotype.Component;
import com.sonnesen.customersapp.application.domain.customer.Customer;
import com.sonnesen.customersapp.application.domain.exception.NotFoundException;
import com.sonnesen.customersapp.application.domain.pagination.Page;
import com.sonnesen.customersapp.application.domain.pagination.Pagination;
import com.sonnesen.customersapp.application.ports.inbound.create.CreateCustomerInput;
import com.sonnesen.customersapp.application.ports.inbound.create.CreateCustomerOutput;
import com.sonnesen.customersapp.application.ports.inbound.create.ForCreatingCustomer;
import com.sonnesen.customersapp.application.ports.inbound.delete.ForDeletingCustomerById;
import com.sonnesen.customersapp.application.ports.inbound.get.ForGettingCustomerById;
import com.sonnesen.customersapp.application.ports.inbound.get.GetCustomerByIdOutput;
import com.sonnesen.customersapp.application.ports.inbound.list.ForListingCustomers;
import com.sonnesen.customersapp.application.ports.inbound.list.ListCustomerOutput;
import com.sonnesen.customersapp.application.ports.inbound.update.ForUpdatingCustomer;
import com.sonnesen.customersapp.application.ports.inbound.update.UpdateCustomerInput;
import com.sonnesen.customersapp.application.ports.inbound.update.UpdateCustomerOutput;
import com.sonnesen.customersapp.application.ports.outbound.repository.CustomerRepository;

@Component
public class CustomerService implements ForCreatingCustomer,
        ForGettingCustomerById,
        ForListingCustomers,
        ForUpdatingCustomer,
        ForDeletingCustomerById {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CreateCustomerOutput create(CreateCustomerInput input) {
        var newCustomer = Customer.newCustomer(input.name(), input.email(), input.phoneNumber(), input.cpf());
        var createdCustomer = customerRepository.create(newCustomer);
        return CreateCustomerOutput.from(createdCustomer);
    }

    @Override
    public GetCustomerByIdOutput getById(Long id) {
        return customerRepository.findById(id)
                .map(GetCustomerByIdOutput::from)
                .orElseThrow(() -> new NotFoundException("Customer with ID %s not found.".formatted(id)));
    }

    @Override
    public Pagination<ListCustomerOutput> list(Page page) {
        return customerRepository.list(page)
                .mapItems(ListCustomerOutput::from);
    }

    @Override
    public UpdateCustomerOutput update(UpdateCustomerInput input) {
        var customer = customerRepository.findById(input.id())
                .orElseThrow(() -> new NotFoundException("Customer with ID %s not found.".formatted(input.id())));

        customer.update(
                input.name(),
                input.email(),
                input.phoneNumber(),
                input.cpf(),
                customer.isActive()
        );

        var updatedCustomer = customerRepository.update(customer);
        return UpdateCustomerOutput.from(updatedCustomer);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

}
