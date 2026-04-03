package com.sonnesen.customerservice.application.usecase.create;

import com.sonnesen.customerservice.domain.model.CPF;
import com.sonnesen.customerservice.domain.model.Email;
import com.sonnesen.customerservice.domain.model.PhoneNumber;

public record CreateCustomerCommand(String name, Email email, PhoneNumber phoneNumber, CPF cpf) {

}
