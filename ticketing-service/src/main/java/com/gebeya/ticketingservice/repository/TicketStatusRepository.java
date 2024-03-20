package com.gebeya.ticketingservice.repository;

import com.gebeya.ticketingservice.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketStatusRepository extends JpaRepository<TicketStatus, Long> {
}
