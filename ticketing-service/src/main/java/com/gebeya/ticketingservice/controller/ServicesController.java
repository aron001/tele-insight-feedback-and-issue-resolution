package com.gebeya.ticketingservice.controller;

import com.gebeya.ticketingservice.model.Services;
import com.gebeya.ticketingservice.service.ServicesService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/ticketing/service")
public class ServicesController {

    private final ServicesService servicesService;

    @GetMapping
    public ResponseEntity<List<Services>> getAll() {
        return servicesService.getAll();
    }

    @PostMapping
    public ResponseEntity<Services> add(@RequestBody Services services) {
        return servicesService.add(services);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Services> update(@RequestBody Services services, @PathVariable(value = "id") int id) {
        return servicesService.update(services, id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id") int id) {
        return servicesService.delete(id);
    }
}
