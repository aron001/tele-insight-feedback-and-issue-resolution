package com.gebeya.ticketingservice.repository;

import com.gebeya.ticketingservice.model.Engineer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EngineerRepository extends JpaRepository<Engineer, Long> {
}
