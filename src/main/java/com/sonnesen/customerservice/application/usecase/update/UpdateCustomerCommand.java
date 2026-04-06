package com.sonnesen.customerservice.application.usecase.update;

import com.sonnesen.customerservice.domain.model.CPF;
import com.sonnesen.customerservice.domain.model.CustomerId;
import com.sonnesen.customerservice.domain.model.CustomerName;
import com.sonnesen.customerservice.domain.model.Email;
import com.sonnesen.customerservice.domain.model.PhoneNumber;

public record UpdateCustomerCommand(CustomerId customerId, CustomerName name, Email email, PhoneNumber phoneNumber,
                CPF cpf) {

}
