package com.gebeya.ticketingservice.repository;

import com.gebeya.ticketingservice.model.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeverityRepository extends JpaRepository<Severity, Long> {
}
