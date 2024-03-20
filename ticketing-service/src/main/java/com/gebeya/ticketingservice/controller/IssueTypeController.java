package com.gebeya.ticketingservice.controller;

import com.gebeya.ticketingservice.dto.requestDto.AddIssueTypeDto;
import com.gebeya.ticketingservice.model.IssueType;
import com.gebeya.ticketingservice.service.IssueTypeService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/ticketing/issueType")
public class IssueTypeController {

    private final IssueTypeService issueTypeService;

    @GetMapping
    public ResponseEntity<List<IssueType>> getAll() {
        return issueTypeService.getAll();
    }

    @PostMapping
    public ResponseEntity<IssueType> add(@RequestBody AddIssueTypeDto issueType) {
        return issueTypeService.add(issueType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IssueType> update(@RequestBody AddIssueTypeDto issueType, @PathVariable(value = "id") int id) {
        return issueTypeService.update(issueType, id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id") int id) {
        return issueTypeService.delete(id);
    }

}
