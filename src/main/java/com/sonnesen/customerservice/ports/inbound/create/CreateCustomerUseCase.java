package com.sonnesen.customerservice.ports.inbound.create;

import com.sonnesen.customerservice.application.usecase.create.CreateCustomerCommand;
import com.sonnesen.customerservice.application.usecase.create.CreateCustomerOutput;

public interface CreateCustomerUseCase {

    CreateCustomerOutput execute(CreateCustomerCommand command);

}