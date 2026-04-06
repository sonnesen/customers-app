package com.sonnesen.customerservice.ports.inbound.activate;

import com.sonnesen.customerservice.domain.model.CustomerId;

public interface ActivateCustomerUseCase {

    void execute(CustomerId customerId);

}