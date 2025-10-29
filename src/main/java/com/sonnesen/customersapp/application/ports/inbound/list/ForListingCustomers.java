package com.sonnesen.customersapp.application.ports.inbound.list;

import com.sonnesen.customersapp.application.domain.pagination.Page;
import com.sonnesen.customersapp.application.domain.pagination.Pagination;

public interface ForListingCustomers {
    Pagination<ListCustomerOutput> list(Page page);
}
