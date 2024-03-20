package com.gebeya.ticketingservice.controller;

import com.gebeya.ticketingservice.dto.requestDto.AddTicketStatusDto;
import com.gebeya.ticketingservice.model.TicketStatus;
import com.gebeya.ticketingservice.service.TicketStatusService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RequestMapping("/api/v1/ticketing/ticketStatus")
@RestController
public class TicketStatusController {

    private final TicketStatusService ticketStatusService;

    @GetMapping
    public ResponseEntity<List<TicketStatus>> getAll() {
        return ticketStatusService.getAll();
    }

    @PostMapping
    public ResponseEntity<TicketStatus> add(@RequestBody AddTicketStatusDto ticketStatus) {
        return ticketStatusService.add(ticketStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketStatus> update(@RequestBody AddTicketStatusDto ticketStatus, @PathVariable(value = "id") int id) {
        return ticketStatusService.update(ticketStatus, id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id") int id) {
        return ticketStatusService.delete(id);
    }
}
