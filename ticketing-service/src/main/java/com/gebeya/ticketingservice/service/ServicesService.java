package com.gebeya.ticketingservice.service;

import com.gebeya.ticketingservice.model.Services;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ServicesService {
    ResponseEntity<List<Services>> getAll();
    ResponseEntity<Services> add(Services service);
    ResponseEntity<Services> update(Services service, int id);
    boolean delete(int id);
    boolean isIdExisted(int id);
    Services getServiceById(int id);
}
