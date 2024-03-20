package com.gebeya.ticketingservice.service;

import com.gebeya.ticketingservice.dto.EngineerServiceTicketDto;
import com.gebeya.ticketingservice.dto.requestDto.AssignTicketDepartmentLocationRequestDto;
import com.gebeya.ticketingservice.dto.requestDto.AssignTicketRequestDto;
import com.gebeya.ticketingservice.model.EngineerServiceTicket;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EngineerServiceTicketService {
    EngineerServiceTicketDto assignTicket(AssignTicketRequestDto dto);
    EngineerServiceTicket assignLocationAndDepartment(AssignTicketDepartmentLocationRequestDto dto);
    ResponseEntity<EngineerServiceTicket> getEngineerTicketById(int engineer_id);
    ResponseEntity<EngineerServiceTicket> getByESTId(int id);
    EngineerServiceTicket getByESTId2(int id);
    ResponseEntity<List<EngineerServiceTicket>> getAll();
    boolean isTicketIdExisted(int id);
}
