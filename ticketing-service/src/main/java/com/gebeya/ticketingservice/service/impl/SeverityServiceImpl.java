package com.gebeya.ticketingservice.service.impl;

import com.gebeya.ticketingservice.model.Severity;
import com.gebeya.ticketingservice.repository.SeverityRepository;
import com.gebeya.ticketingservice.service.SeverityService;
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
public class SeverityServiceImpl implements SeverityService {

    private final SeverityRepository severityRepository;

    public ResponseEntity<List<Severity>> getAll() {
        return new ResponseEntity<>(severityRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Severity> add(Severity severity) {
        if (isExisted(severity)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Severity is already registered");
        }
        severity.setCreated_By("admin"); //will do after authentication
        severity.setCreated_date(new Date().toInstant());
        Severity savedSeverity = save(severity);
        return new ResponseEntity<>(savedSeverity, HttpStatus.CREATED);
    }

    public ResponseEntity<Severity> update(Severity severity, int id) {
        if (isIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Severity Id is not found");
        }
        Optional<Severity> optionalSeverity = severityRepository.findById((long) id);
        if (optionalSeverity.isPresent()) {

            Severity existedSeverity = optionalSeverity.get();
            if (!severity.getName().isEmpty()) {
                existedSeverity.setName(severity.getName());
            }
            if (!severity.getDescription().isEmpty()) {
                existedSeverity.setDescription(severity.getDescription());
            }
            existedSeverity.setUpdated_at(new Date().toInstant());
            existedSeverity.setUpdated_by("admin"); //will change to the actual user
            existedSeverity = save(existedSeverity);

            return new ResponseEntity<>(existedSeverity, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Severity Id is not found");
    }

    public boolean delete(int id) {
        if (isIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Severity Id is not found");
        }
        severityRepository.deleteById((long) id);
        return true;
    }

    private Severity save(Severity severity) {
        return severityRepository.save(severity);
    }

    private boolean isExisted(Severity severity) {
        List<Severity> severityList = severityRepository.findAll();
        boolean isExisted = false;
        for (Severity severity1 : severityList) {
            if (severity1.getName().equalsIgnoreCase(severity.getName())) {
                isExisted = true;
                break;
            }
        }
        return isExisted;
    }

    public Severity getSeverityById(int id) {
        Optional<Severity> optionalSeverity = severityRepository.findById((long) id);
        return optionalSeverity.orElse(null);
    }

    public boolean isIdExisted(int id) {
        return !severityRepository.existsById((long) id);
    }

}
