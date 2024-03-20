package com.gebeya.ticketingservice.controller;

import com.gebeya.ticketingservice.dto.requestDto.AddWorkTitleDto;
import com.gebeya.ticketingservice.model.WorkTitle;
import com.gebeya.ticketingservice.service.WorkTitleService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/ticketing/workTitle")
public class WorkTitleController {
    private final WorkTitleService workTitleService;

    @GetMapping
    public ResponseEntity<List<WorkTitle>> getAll() {
        return workTitleService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<WorkTitle> getById(@PathVariable(value="id") int id) {
        return workTitleService.getById(id);
    }

    @PostMapping
    public ResponseEntity<WorkTitle> add(@RequestBody AddWorkTitleDto workTitleDto) {
        return workTitleService.add(workTitleDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkTitle> update(@RequestBody AddWorkTitleDto workTitleDto, @PathVariable(value = "id") int id) {
        return workTitleService.update(workTitleDto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable(value = "id") int id) {
        return workTitleService.delete(id);
    }


}
