package com.gebeya.ticketingservice.repository;

import com.gebeya.ticketingservice.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
