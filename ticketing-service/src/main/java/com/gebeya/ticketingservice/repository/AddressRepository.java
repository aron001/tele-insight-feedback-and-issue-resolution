package com.gebeya.ticketingservice.repository;

import com.gebeya.ticketingservice.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
