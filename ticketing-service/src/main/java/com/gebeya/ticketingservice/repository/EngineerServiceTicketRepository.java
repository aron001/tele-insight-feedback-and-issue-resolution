package com.gebeya.ticketingservice.repository;

import com.gebeya.ticketingservice.model.EngineerServiceTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EngineerServiceTicketRepository extends JpaRepository<EngineerServiceTicket, Long> {
}
