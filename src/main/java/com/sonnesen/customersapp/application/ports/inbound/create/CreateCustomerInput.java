package com.sonnesen.customersapp.application.ports.inbound.create;

public record CreateCustomerInput(String name, String email, String phoneNumber, String cpf) {

}
