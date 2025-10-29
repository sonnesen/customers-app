package com.sonnesen.customersapp.infrastructure.adapters.outbound.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sonnesen.customersapp.infrastructure.adapters.outbound.persistence.entity.CustomerJpaEntity;

public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, Long> {

}
