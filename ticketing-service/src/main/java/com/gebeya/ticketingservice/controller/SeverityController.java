package com.gebeya.ticketingservice.controller;

import com.gebeya.ticketingservice.model.Severity;
import com.gebeya.ticketingservice.service.SeverityService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/ticketing/severity")
public class SeverityController {

    private final SeverityService severityService;

    @GetMapping
    public ResponseEntity<List<Severity>> getAll() {
        return severityService.getAll();
    }

    @PostMapping
    public ResponseEntity<Severity> add(@RequestBody Severity severity) {
        return severityService.add(severity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Severity> update(@RequestBody Severity severity, @PathVariable(value = "id") int id) {
        return severityService.update(severity, id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id") int id) {
        return severityService.delete(id);
    }
}
