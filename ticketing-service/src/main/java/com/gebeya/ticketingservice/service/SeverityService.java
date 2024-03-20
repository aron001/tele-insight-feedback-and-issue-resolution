package com.gebeya.ticketingservice.service;

import com.gebeya.ticketingservice.model.Severity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SeverityService {
    ResponseEntity<List<Severity>> getAll();
    ResponseEntity<Severity> add(Severity severity);
    ResponseEntity<Severity> update(Severity severity, int id);
    boolean delete(int id);
    boolean isIdExisted(int id);
    Severity getSeverityById(int id);
}
