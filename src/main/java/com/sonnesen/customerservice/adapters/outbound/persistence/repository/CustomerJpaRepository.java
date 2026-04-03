package com.sonnesen.customerservice.adapters.outbound.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sonnesen.customerservice.adapters.outbound.persistence.entity.CustomerJpaEntity;

public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsByCpf(String value);

}
