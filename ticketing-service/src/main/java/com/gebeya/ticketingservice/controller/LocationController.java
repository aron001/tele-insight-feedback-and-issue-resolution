package com.gebeya.ticketingservice.controller;

import com.gebeya.ticketingservice.model.Location;
import com.gebeya.ticketingservice.service.LocationService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/ticketing/location")
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<List<Location>> getAll() {
        return locationService.getAll();
    }

    @PostMapping
    public ResponseEntity<Location> add(@RequestBody Location location) {
        return locationService.add(location);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> update(@RequestBody Location location, @PathVariable(value = "id") int id) {
        return locationService.update(location, id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id") int id) {
        return locationService.delete(id);
    }

}
