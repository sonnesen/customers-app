package com.sonnesen.customersapp.application.ports.inbound.update;

public record UpdateCustomerInput(Long id, String name, String email, String phoneNumber, String cpf) {

}
