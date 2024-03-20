package com.gebeya.ticketingservice.service.impl;

import com.gebeya.ticketingservice.model.Location;
import com.gebeya.ticketingservice.repository.LocationRepository;
import com.gebeya.ticketingservice.service.LocationService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    public ResponseEntity<List<Location>> getAll() {
        return new ResponseEntity<>(locationRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Location> add(Location location) {
        if (isExisted(location)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Location is already registered");
        }
        location.setCreated_By("admin"); //will do after authentication
        location.setCreated_date(new Date().toInstant());
        Location savedLocation = save(location);
        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
    }

    public ResponseEntity<Location> update(Location location, int id) {
        if (isIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location Id is not found");
        }
        Optional<Location> optionalLocation = locationRepository.findById((long) id);
        if (optionalLocation.isPresent()) {
            Location existedLocation = optionalLocation.get();
            if (!location.getName().isEmpty()) {
                existedLocation.setName(location.getName());
            }
            if (!location.getDescription().isEmpty()) {
                existedLocation.setDescription(location.getDescription());
            }
            existedLocation.setUpdated_at(new Date().toInstant());
            existedLocation.setUpdated_by("admin"); //will change to the actual user
            existedLocation = save(existedLocation);

            return new ResponseEntity<>(existedLocation, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location Id is not found");
    }

    public boolean delete(int id) {
        if (isIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location Id is not found");
        }
        locationRepository.deleteById((long) id);
        return true;
    }

    private Location save(Location location) {
        return locationRepository.save(location);
    }

    private boolean isExisted(Location location) {
        List<Location> locationList = locationRepository.findAll();
        boolean isExisted = false;
        for (Location location1 : locationList) {
            if (location1.getName().equalsIgnoreCase(location.getName())) {
                isExisted = true;
                break;
            }
        }
        return isExisted;
    }

    public boolean isIdExisted(int id) {
        return !locationRepository.existsById((long) id);
    }

    public Location getById(int id) {
        Optional<Location> optionalLocation = locationRepository.findById((long) id);
        return optionalLocation.orElse(null);
    }
}
