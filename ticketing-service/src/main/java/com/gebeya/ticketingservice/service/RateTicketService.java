package com.gebeya.ticketingservice.service;

import com.gebeya.ticketingservice.dto.requestDto.RateTicketRequestDto;
import com.gebeya.ticketingservice.model.RateTicket;
import org.springframework.http.ResponseEntity;

public interface RateTicketService {
    ResponseEntity<RateTicket> add(RateTicketRequestDto dto);
    public RateTicket getByTicketId(int ticket_id);
}
