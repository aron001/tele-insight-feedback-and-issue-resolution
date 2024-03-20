package com.gebeya.ticketingservice.service;

import com.gebeya.ticketingservice.dto.requestDto.AddTicketStatusDto;
import com.gebeya.ticketingservice.model.TicketStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TicketStatusService {
    ResponseEntity<List<TicketStatus>> getAll();
    ResponseEntity<TicketStatus> add(AddTicketStatusDto ticketStatus);
    ResponseEntity<TicketStatus> update(AddTicketStatusDto ticketStatus, int id);
    boolean delete(int id);
    boolean isIdExisted(int id);
    int getTicketStatusId(String name);
    String getTicketStatusName(int id);
    TicketStatus getTicketStatusByName(String name);
    TicketStatus getTicketStatusById(int id);
}
