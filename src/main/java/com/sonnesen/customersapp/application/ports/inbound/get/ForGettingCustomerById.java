package com.sonnesen.customersapp.application.ports.inbound.get;

public interface ForGettingCustomerById {
    GetCustomerByIdOutput getById(Long id);
}
