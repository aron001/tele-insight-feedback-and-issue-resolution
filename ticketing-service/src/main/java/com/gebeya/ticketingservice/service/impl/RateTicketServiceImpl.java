package com.gebeya.ticketingservice.service.impl;

import com.gebeya.ticketingservice.dto.requestDto.RateTicketRequestDto;
import com.gebeya.ticketingservice.model.RateTicket;
import com.gebeya.ticketingservice.model.Ticket;
import com.gebeya.ticketingservice.repository.RateTicketRepository;
import com.gebeya.ticketingservice.service.RateTicketService;
import com.gebeya.ticketingservice.service.TicketService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class RateTicketServiceImpl implements RateTicketService {

    private final RateTicketRepository rateTicketRepository;
    private final TicketService ticketService;

    public ResponseEntity<RateTicket> add(RateTicketRequestDto dto) {
        List<RateTicket> optionalRateTicket = rateTicketRepository.findAll();
        for (RateTicket ticket : optionalRateTicket) {
            if (ticket.getTicket().getId() == dto.getTicket_id()) {
                throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, "This Ticket already Rated!");
            }
        }
        Optional<Ticket> optionalTicket = Optional.ofNullable(ticketService.getById(dto.getTicket_id()));
        if (optionalTicket.isPresent()) {
            Ticket existedTicket = optionalTicket.get();
            if (!existedTicket.getTicketStatus().getName().equalsIgnoreCase("resolved")) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ticket is not Resolved yet. Please Rate after the ticket is resolved");
            }
            if (dto.getRate() <= 5 && dto.getRate() >= 0) {
                RateTicket ticket = new RateTicket();
                ticket.setComment(dto.getComment());
                ticket.setTicket(optionalTicket.get());
                ticket.setRate(dto.getRate());
                ticket.setCreated_By(getUsername());
                ticket.setIs_active(true);
                ticket.setCreated_date(new Date().toInstant());
                return new ResponseEntity<>(rateTicketRepository.save(ticket), HttpStatus.CREATED);
            } else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, " Rate must be between 0 - 5");
            }
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Ticket id is not found");
    }

    public RateTicket getByTicketId(int ticket_id) {
        List<RateTicket> rateTickets = rateTicketRepository.findAll();
        RateTicket ticket = new RateTicket();
        for (RateTicket rateTicket : rateTickets) {
            if (rateTicket.getTicket().getId() == ticket_id) {
                ticket.setRate(rateTicket.getRate());
                ticket.setComment(rateTicket.getComment());
                ticket.setCreated_By(rateTicket.getCreated_By());
                ticket.setId(rateTicket.getId());
                ticket.setTicket(rateTicket.getTicket());
                break;
            }
        }
        if (ticket.getRate() != -1)
            return ticket;

        return null;
    }
    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
