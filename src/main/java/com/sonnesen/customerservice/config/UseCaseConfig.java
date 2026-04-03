package com.sonnesen.customerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sonnesen.customerservice.application.usecase.activate.ActivateCustomerUseCase;
import com.sonnesen.customerservice.application.usecase.create.CreateCustomerUseCase;
import com.sonnesen.customerservice.application.usecase.deactivate.DeactivateCustomerUseCase;
import com.sonnesen.customerservice.application.usecase.get.GetCustomerByIdUseCase;
import com.sonnesen.customerservice.application.usecase.list.ListCustomersUseCase;
import com.sonnesen.customerservice.application.usecase.update.UpdateCustomerUseCase;
import com.sonnesen.customerservice.ports.outbound.persistence.repository.CustomerRepositoryPort;

@Configuration
public class UseCaseConfig {

    @Bean
    ActivateCustomerUseCase activateCustomerUseCase(final CustomerRepositoryPort customerRepository) {
        return new ActivateCustomerUseCase(customerRepository);
    }

    @Bean
    CreateCustomerUseCase createCustomerUseCase(final CustomerRepositoryPort customerRepository) {
        return new CreateCustomerUseCase(customerRepository);
    }

    @Bean
    DeactivateCustomerUseCase deactivateCustomerUseCase(final CustomerRepositoryPort customerRepository) {
        return new DeactivateCustomerUseCase(customerRepository);
    }

    @Bean
    GetCustomerByIdUseCase getCustomerByIdUseCase(final CustomerRepositoryPort customerRepository) {
        return new GetCustomerByIdUseCase(customerRepository);
    }

    @Bean
    ListCustomersUseCase listCustomersUseCase(final CustomerRepositoryPort customerRepository) {
        return new ListCustomersUseCase(customerRepository);
    }

    @Bean
    UpdateCustomerUseCase updateCustomerUseCase(final CustomerRepositoryPort customerRepository) {
        return new UpdateCustomerUseCase(customerRepository);
    }
}
