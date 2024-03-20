package com.gebeya.ticketingservice.repository;

import com.gebeya.ticketingservice.model.Ticket;
import com.gebeya.ticketingservice.repository.specification.TicketSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {

    default Optional<Ticket> findBySrNumber(String srNumber){
        Specification<Ticket> spec = TicketSpecification.withSrNumber(srNumber)
                .and(TicketSpecification.joinSeverity())
                .and(TicketSpecification.joinIssueType())
                .and(TicketSpecification.joinTicketStatus())
                .and(TicketSpecification.joinServices());

        return findOne(spec);
    }
}
