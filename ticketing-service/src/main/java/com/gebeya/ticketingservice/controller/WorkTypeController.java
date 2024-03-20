package com.gebeya.ticketingservice.controller;

import com.gebeya.ticketingservice.dto.requestDto.AddWorkTypeDto;
import com.gebeya.ticketingservice.model.WorkType;
import com.gebeya.ticketingservice.service.WorkTypeService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/ticketing/workType")
public class WorkTypeController {
    private final WorkTypeService workTypeService;

    @GetMapping
    public ResponseEntity<List<WorkType>> getAll() {
        return workTypeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkType> getById(@PathVariable(value = "id") int id) {
        return workTypeService.getById(id);
    }

    @PostMapping
    public ResponseEntity<WorkType> add(@RequestBody AddWorkTypeDto workType) {
        return workTypeService.add(workType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkType> update(@RequestBody AddWorkTypeDto workType, @PathVariable(value = "id") int id) {
        return workTypeService.update(workType, id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id") int id) {
        return workTypeService.delete(id);
    }

}
