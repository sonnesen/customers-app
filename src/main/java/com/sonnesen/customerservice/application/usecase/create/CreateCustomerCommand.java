package com.sonnesen.customerservice.application.usecase.create;

import com.sonnesen.customerservice.domain.model.CPF;
import com.sonnesen.customerservice.domain.model.CustomerName;
import com.sonnesen.customerservice.domain.model.Email;
import com.sonnesen.customerservice.domain.model.PhoneNumber;

public record CreateCustomerCommand(CustomerName name, Email email, PhoneNumber phoneNumber, CPF cpf) {

}
