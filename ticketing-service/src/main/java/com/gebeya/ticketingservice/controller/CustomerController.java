package com.gebeya.ticketingservice.controller;

import com.gebeya.ticketingservice.dto.TicketRequestRespondDto;
import com.gebeya.ticketingservice.dto.requestDto.CreateTicketDto;
import com.gebeya.ticketingservice.dto.requestDto.GetCustomerTicketDto;
import com.gebeya.ticketingservice.dto.requestDto.RateTicketRequestDto;
import com.gebeya.ticketingservice.model.RateTicket;
import com.gebeya.ticketingservice.service.CustomerService;
import com.gebeya.ticketingservice.service.RateTicketService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/ticketing/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final RateTicketService rateTicketService;

    @PostMapping("/createTicket")
    public ResponseEntity<TicketRequestRespondDto> createTicket(@RequestBody CreateTicketDto createTicketDto) {
        return customerService.createTicket(createTicketDto);
    }

    @GetMapping("/allTickets")
    public ResponseEntity<List<TicketRequestRespondDto>> getAllCustomerTicket() {
        return customerService.getAllCustomerTicket();
    }

    @GetMapping("/unResolvedTicket")
    public ResponseEntity<List<TicketRequestRespondDto>> getCustomerUnresolvedTicket(@RequestBody GetCustomerTicketDto dto) {
        return customerService.getCustomerUnresolvedTicket(dto);
    }
    @PutMapping("/updateTicket/{id}")
    public ResponseEntity<TicketRequestRespondDto> updateTicket(@RequestBody CreateTicketDto dto, @PathVariable(value = "id") int id){
        return customerService.updateTicket(dto,id);
    }
    @PostMapping("/rateTicket")
    public ResponseEntity<RateTicket> rateTicket(@RequestBody RateTicketRequestDto dto){
        return rateTicketService.add(dto);
    }
}
