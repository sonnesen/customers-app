package com.sonnesen.customerservice.ports.inbound.update;

import com.sonnesen.customerservice.application.usecase.update.UpdateCustomerCommand;

public interface UpdateCustomerUseCase {

    void execute(UpdateCustomerCommand command);

}