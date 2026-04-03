package com.sonnesen.customerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sonnesen.customerservice.adapters.outbound.persistence.CustomerRepositoryAdapter;
import com.sonnesen.customerservice.adapters.outbound.persistence.mapper.CustomerPersistenceMapper;
import com.sonnesen.customerservice.adapters.outbound.persistence.repository.CustomerJpaRepository;

@Configuration
public class RepositoryConfig {

    @Bean
    CustomerRepositoryAdapter customerRepositoryAdapter(final CustomerJpaRepository customerJpaRepository,
            final CustomerPersistenceMapper customerPersistenceMapper) {
        return new CustomerRepositoryAdapter(customerJpaRepository, customerPersistenceMapper);
    }
}
