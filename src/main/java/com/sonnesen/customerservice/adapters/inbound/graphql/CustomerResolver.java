package com.sonnesen.customerservice.adapters.inbound.graphql;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.sonnesen.customerservice.adapters.inbound.graphql.dto.GraphqlCustomerDTO;
import com.sonnesen.customerservice.adapters.inbound.graphql.dto.GraphqlPaginatedCustomersDTO;
import com.sonnesen.customerservice.application.mapper.CustomerApplicationMapper;
import com.sonnesen.customerservice.application.usecase.get.GetCustomerByIdUseCase;
import com.sonnesen.customerservice.application.usecase.list.ListCustomersUseCase;
import com.sonnesen.customerservice.domain.model.Page;

@Controller
public class CustomerResolver {

    private static final Logger log = LoggerFactory.getLogger(CustomerResolver.class);

    private final ListCustomersUseCase listCustomersUseCase;
    private final GetCustomerByIdUseCase getCustomerByIdUseCase;
    private final CustomerApplicationMapper customerMapper;

    public CustomerResolver(final ListCustomersUseCase listCustomersUseCase,
            final GetCustomerByIdUseCase getCustomerByIdUseCase,
            final CustomerApplicationMapper customerMapper) {
        this.listCustomersUseCase = Objects.requireNonNull(listCustomersUseCase,
                "listCustomersUseCase must not be null");
        this.getCustomerByIdUseCase = Objects.requireNonNull(getCustomerByIdUseCase,
                "getCustomerByIdUseCase must not be null");
        this.customerMapper = Objects.requireNonNull(customerMapper, "customerMapper must not be null");
    }

    @QueryMapping
    public GraphqlCustomerDTO getCustomer(@Argument("id") final String id) {
        final var customerId = getCustomerId(id);
        final var output = getCustomerByIdUseCase.execute(customerId);
        return customerMapper.toGraphqlDTO(output);
    }

    @QueryMapping
    public GraphqlPaginatedCustomersDTO listCustomers(@Argument("page") final Integer page,
            @Argument("perPage") final Integer perPage) {
        final var listCustomers = listCustomersUseCase.execute(new Page(page, perPage));
        final var customers = listCustomers.mapItems(customerMapper::toGraphqlDTO);
        return new GraphqlPaginatedCustomersDTO(
                customers.items(),
                customers.totalItems(),
                customers.currentPage(),
                customers.perPage());
    }

    private long getCustomerId(final String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            log.error("Invalid customer ID format: {}. Returning -1 as default.", id, e);
            return -1;
        }
    }

}
