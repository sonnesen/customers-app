package com.sonnesen.customersapp.infrastructure.adapters.inbound.graphql;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import com.sonnesen.customersapp.application.domain.pagination.Page;
import com.sonnesen.customersapp.application.ports.inbound.get.ForGettingCustomerById;
import com.sonnesen.customersapp.application.ports.inbound.list.ForListingCustomers;
import com.sonnesen.customersapp.infrastructure.adapters.inbound.graphql.dto.GraphqlCustomerDTO;
import com.sonnesen.customersapp.infrastructure.adapters.inbound.graphql.dto.GraphqlPaginatedCustomersDTO;
import com.sonnesen.customersapp.infrastructure.adapters.inbound.mapper.CustomerMapper;

@Controller
public class CustomerResolver { // Adapter for GraphQL API
    private static final Logger log = LoggerFactory.getLogger(CustomerResolver.class);

    private final ForListingCustomers listingCustomersPort;
    private final ForGettingCustomerById gettingCustomerByIdPort;
    private final CustomerMapper customerMapper;

    public CustomerResolver(final ForListingCustomers listingCustomersPort,
                            final ForGettingCustomerById gettingCustomerByIdPort,
                            final CustomerMapper customerMapper) {
        this.listingCustomersPort = Objects.requireNonNull(listingCustomersPort);
        this.gettingCustomerByIdPort = Objects.requireNonNull(gettingCustomerByIdPort);
        this.customerMapper = Objects.requireNonNull(customerMapper);
    }

    @QueryMapping
    public GraphqlCustomerDTO getCustomer(@Argument("id") final String id) {
        var customerId = getCustomerId(id);
        var output = gettingCustomerByIdPort.getById(customerId);
        return customerMapper.toGraphqlDTO(output);
    }

    @QueryMapping
    public GraphqlPaginatedCustomersDTO listCustomers(@Argument("page") final Integer page,
                                                      @Argument("perPage") final Integer perPage) {
        var listCustomers = listingCustomersPort.list(new Page(page, perPage));
        var customers = listCustomers.mapItems(customerMapper::toGraphqlDTO);
        var paginatedCustomers = new GraphqlPaginatedCustomersDTO(
            customers.items(),
            customers.totalItems(),
            customers.currentPage(),
            customers.perPage()
        );
        return paginatedCustomers;
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
