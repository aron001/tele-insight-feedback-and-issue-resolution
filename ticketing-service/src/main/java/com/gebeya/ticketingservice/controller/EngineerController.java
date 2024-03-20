package com.gebeya.ticketingservice.controller;

import com.gebeya.ticketingservice.dto.EngineerServiceTicketDto;
import com.gebeya.ticketingservice.dto.requestDto.AssignTicketDepartmentLocationRequestDto;
import com.gebeya.ticketingservice.dto.requestDto.AssignTicketRequestDto;
import com.gebeya.ticketingservice.model.EngineerServiceTicket;
import com.gebeya.ticketingservice.service.EngineerService;
import com.gebeya.ticketingservice.service.EngineerServiceTicketService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/ticketing/engineer")
public class EngineerController {

    private final EngineerService engineerService;
    private final EngineerServiceTicketService engineerServiceTicketService;

    @GetMapping
    public ResponseEntity<List<EngineerServiceTicket>> getAll() {
        return engineerServiceTicketService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EngineerServiceTicket> getByESTId(@PathVariable(value = "id") int id) {
        return engineerServiceTicketService.getByESTId(id);
    }

    @GetMapping("/getTicketByEngineerId/{id}")
    public ResponseEntity<EngineerServiceTicket> getByEngineerId(@PathVariable(value = "id") int id) {
        return engineerServiceTicketService.getEngineerTicketById(id);
    }


    @PostMapping("/assignTicket")
    public EngineerServiceTicketDto assignEngineer(@RequestBody AssignTicketRequestDto dto) {
        return engineerServiceTicketService.assignTicket(dto);
    }

    @PostMapping("/assignLocation")
    public EngineerServiceTicket addLocationDepartment(@RequestBody AssignTicketDepartmentLocationRequestDto dto) {
        return engineerServiceTicketService.assignLocationAndDepartment(dto);
    }
}
