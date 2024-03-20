package com.gebeya.ticketingservice.service.impl;

import com.gebeya.ticketingservice.model.Services;
import com.gebeya.ticketingservice.repository.ServicesRepository;
import com.gebeya.ticketingservice.service.ServicesService;
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
public class ServiceServiceImpl implements ServicesService {

    private final ServicesRepository serviceRepository;

    public ResponseEntity<List<Services>> getAll() {
        return new ResponseEntity<>(serviceRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Services> add(Services service) {
        if (isExisted(service)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Service is already registered");
        }
        service.setCreated_By("admin"); //will do after authentication
        service.setCreated_date(new Date().toInstant());
        Services savedService = save(service);
        return new ResponseEntity<>(savedService, HttpStatus.CREATED);
    }

    public ResponseEntity<Services> update(Services service, int id) {
        if (isIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service Id is not found");
        }
        Optional<Services> optionalServices = serviceRepository.findById((long) id);
        if (optionalServices.isPresent()) {
            Services existedService = optionalServices.get();


            if (!service.getName().isEmpty()) {
                existedService.setName(service.getName());
            }
            if (!service.getDescription().isEmpty()) {
                existedService.setDescription(service.getDescription());
            }
            existedService.setUpdated_at(new Date().toInstant());
            existedService.setUpdated_by("admin"); //will change to the actual user
            existedService = save(existedService);

            return new ResponseEntity<>(existedService, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service Id is not found");
    }

    public boolean delete(int id) {
        if (isIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service Id is not found");
        }
        serviceRepository.deleteById((long) id);
        return true;
    }

    private Services save(Services service) {
        return serviceRepository.save(service);
    }

    private boolean isExisted(Services service) {
        boolean isExisted = false;
        List<Services> servicesList = serviceRepository.findAll();
        if (servicesList.isEmpty()) {
            return isExisted;
        }
        for (Services services : servicesList) {
            if (services.getName().equalsIgnoreCase(service.getName())) {
                isExisted = true;
                break;
            }
        }
        return isExisted;
    }

    public Services getServiceById(int id) {
        Optional<Services> optionalServices = serviceRepository.findById((long) id);
        return optionalServices.orElse(null);
    }

    public boolean isIdExisted(int id) {
        return !serviceRepository.existsById((long) id);
    }
}
