package com.sonnesen.customerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sonnesen.customerservice.application.usecase.activate.DefaultActivateCustomerUseCase;
import com.sonnesen.customerservice.application.usecase.create.DefaultCreateCustomerUseCase;
import com.sonnesen.customerservice.application.usecase.deactivate.DefaultDeactivateCustomerUseCase;
import com.sonnesen.customerservice.application.usecase.get.DefaultGetCustomerByIdUseCase;
import com.sonnesen.customerservice.application.usecase.list.DefaultListCustomersUseCase;
import com.sonnesen.customerservice.application.usecase.update.DefaultUpdateCustomerUseCase;
import com.sonnesen.customerservice.ports.inbound.activate.ActivateCustomerUseCase;
import com.sonnesen.customerservice.ports.inbound.create.CreateCustomerUseCase;
import com.sonnesen.customerservice.ports.inbound.deactivate.DeactivateCustomerUseCase;
import com.sonnesen.customerservice.ports.inbound.get.GetCustomerByIdUseCase;
import com.sonnesen.customerservice.ports.inbound.list.ListCustomersUseCase;
import com.sonnesen.customerservice.ports.inbound.update.UpdateCustomerUseCase;
import com.sonnesen.customerservice.ports.outbound.persistence.repository.CustomerRepositoryPort;

@Configuration
public class UseCaseConfig {

    @Bean
    ActivateCustomerUseCase activateCustomerUseCase(final CustomerRepositoryPort customerRepository) {
        return new DefaultActivateCustomerUseCase(customerRepository);
    }

    @Bean
    CreateCustomerUseCase createCustomerUseCase(final CustomerRepositoryPort customerRepository) {
        return new DefaultCreateCustomerUseCase(customerRepository);
    }

    @Bean
    DeactivateCustomerUseCase deactivateCustomerUseCase(final CustomerRepositoryPort customerRepository) {
        return new DefaultDeactivateCustomerUseCase(customerRepository);
    }

    @Bean
    GetCustomerByIdUseCase getCustomerByIdUseCase(final CustomerRepositoryPort customerRepository) {
        return new DefaultGetCustomerByIdUseCase(customerRepository);
    }

    @Bean
    ListCustomersUseCase listCustomersUseCase(final CustomerRepositoryPort customerRepository) {
        return new DefaultListCustomersUseCase(customerRepository);
    }

    @Bean
    UpdateCustomerUseCase updateCustomerUseCase(final CustomerRepositoryPort customerRepository) {
        return new DefaultUpdateCustomerUseCase(customerRepository);
    }
}
