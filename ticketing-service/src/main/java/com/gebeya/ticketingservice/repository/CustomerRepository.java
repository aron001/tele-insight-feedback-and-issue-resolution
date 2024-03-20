package com.gebeya.ticketingservice.repository;

import com.gebeya.ticketingservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
