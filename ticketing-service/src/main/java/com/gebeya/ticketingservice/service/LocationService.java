package com.gebeya.ticketingservice.service;

import com.gebeya.ticketingservice.model.Location;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LocationService {
    ResponseEntity<List<Location>> getAll();
    ResponseEntity<Location> add(Location location);
    ResponseEntity<Location> update(Location location, int id);
    boolean delete(int id);
    boolean isIdExisted(int id);
    Location getById(int id);
}
