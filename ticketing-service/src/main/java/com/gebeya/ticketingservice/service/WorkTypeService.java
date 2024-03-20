package com.gebeya.ticketingservice.service;

import com.gebeya.ticketingservice.dto.requestDto.AddWorkTypeDto;
import com.gebeya.ticketingservice.model.WorkType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WorkTypeService {
    ResponseEntity<List<WorkType>> getAll();
    ResponseEntity<WorkType> getById(int id);
    WorkType getById2(int id);
    ResponseEntity<WorkType> add(AddWorkTypeDto workType);
    ResponseEntity<WorkType> update(AddWorkTypeDto workType, int id);
    boolean delete(int id);
    boolean isIdExisted(int id);
}
