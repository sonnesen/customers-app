package com.sonnesen.customerservice.ports.inbound.deactivate;

import com.sonnesen.customerservice.domain.model.CustomerId;

public interface DeactivateCustomerUseCase {

    void execute(CustomerId customerId);

}