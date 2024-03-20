package com.gebeya.ticketingservice.service;

import com.gebeya.ticketingservice.dto.requestDto.AddEngineerDto;
import com.gebeya.ticketingservice.model.Engineer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EngineerService {

    ResponseEntity<List<Engineer>> getAll();

    ResponseEntity<Engineer> add(AddEngineerDto engineerDto);

    ResponseEntity<Engineer> update(AddEngineerDto engineerDto, int id);

    boolean isEngineerExisted(int id);

    Engineer getById(int id);
}
