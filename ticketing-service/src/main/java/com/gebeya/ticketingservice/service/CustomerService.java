package com.gebeya.ticketingservice.service;

import com.gebeya.ticketingservice.dto.TicketRequestRespondDto;
import com.gebeya.ticketingservice.dto.requestDto.AddCustomerDto;
import com.gebeya.ticketingservice.dto.requestDto.CreateTicketDto;
import com.gebeya.ticketingservice.dto.requestDto.GetCustomerTicketDto;
import com.gebeya.ticketingservice.model.Customer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    ResponseEntity<Customer> add(AddCustomerDto addCustomerDto);

    ResponseEntity<List<Customer>> getAll();

    ResponseEntity<Customer> getById(int id);
    Customer getByCustomerId(int id);

    ResponseEntity<Customer> update(AddCustomerDto addCustomerDto, int id);

    ResponseEntity<TicketRequestRespondDto> createTicket(CreateTicketDto ticket);
    ResponseEntity<TicketRequestRespondDto> updateTicket(CreateTicketDto createTicketDto, int id);

    ResponseEntity<List<TicketRequestRespondDto>> getCustomerUnresolvedTicket(GetCustomerTicketDto dto);

    ResponseEntity<List<TicketRequestRespondDto>> getAllCustomerTicket();
}
