package com.sonnesen.customerservice.ports.inbound.list;

import com.sonnesen.customerservice.application.usecase.list.CustomerListOutput;
import com.sonnesen.customerservice.domain.model.Page;
import com.sonnesen.customerservice.domain.model.Pagination;

public interface ListCustomersUseCase {

    Pagination<CustomerListOutput> execute(Page page);

}