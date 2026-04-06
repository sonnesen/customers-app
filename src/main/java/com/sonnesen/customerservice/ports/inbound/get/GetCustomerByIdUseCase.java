package com.sonnesen.customerservice.ports.inbound.get;

import com.sonnesen.customerservice.application.usecase.get.CustomerOutput;
import com.sonnesen.customerservice.domain.model.CustomerId;

public interface GetCustomerByIdUseCase {

    CustomerOutput execute(CustomerId customerId);

}