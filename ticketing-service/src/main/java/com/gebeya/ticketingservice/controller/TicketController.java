package com.gebeya.ticketingservice.controller;

import com.gebeya.ticketingservice.dto.AssignedTicketDto;
import com.gebeya.ticketingservice.model.Ticket;
import com.gebeya.ticketingservice.service.TicketService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/ticketing/ticket")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/assignTicket/{engineerId}/{ticketId}")
    public AssignedTicketDto assignTicket(@PathVariable(value = "engineerId") int engineerId, @PathVariable(value = "ticketId") int ticketId) {
        return ticketService.assignTicket(engineerId, ticketId);
    }
    @GetMapping("/getAllTicket")
    public List<Ticket> getAllTicket(){
        return ticketService.getAllTickets();
    }

}
