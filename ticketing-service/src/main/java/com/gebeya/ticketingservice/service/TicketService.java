package com.gebeya.ticketingservice.service;

import com.gebeya.ticketingservice.dto.AssignedTicketDto;
import com.gebeya.ticketingservice.dto.TicketRequestRespondDto;
import com.gebeya.ticketingservice.model.Ticket;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    Ticket createTicket(Ticket ticket);

    Optional<Ticket> findTicketBySrNumber(String srNumber);
    List<Ticket> findAllTicketsByCustomerId(int id);
    List<Ticket> findUnResolvedTicketsByCustomerId(int id);
    List<Ticket> getAllTickets();
    boolean isTicketIdExisted(int id);
    Ticket getById(int id);
    Ticket update (Ticket  ticket);
    AssignedTicketDto assignTicket(int engineerId, int ticketId);
}
