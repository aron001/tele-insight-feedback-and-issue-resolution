package com.gebeya.ticketingservice.service;

import com.gebeya.ticketingservice.dto.requestDto.AddWorkTitleDto;
import com.gebeya.ticketingservice.model.WorkTitle;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WorkTitleService {
    ResponseEntity<WorkTitle> add(AddWorkTitleDto workTitleDto);

    ResponseEntity<List<WorkTitle>> getAll();

    ResponseEntity<WorkTitle> getById(int id);
    WorkTitle getById2(int id);

    ResponseEntity<WorkTitle> update(AddWorkTitleDto addWorkTitleDto, int id);

    ResponseEntity<Boolean> delete(int id);

    boolean isExisted(int id);
}
