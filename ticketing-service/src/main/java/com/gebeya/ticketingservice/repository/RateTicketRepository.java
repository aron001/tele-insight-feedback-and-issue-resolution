package com.gebeya.ticketingservice.repository;

import com.gebeya.ticketingservice.model.RateTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateTicketRepository extends JpaRepository<RateTicket, Long> {
}
