package com.gebeya.ticketingservice.controller;

import com.gebeya.ticketingservice.dto.requestDto.AddDepartmentRequestDto;
import com.gebeya.ticketingservice.model.Department;
import com.gebeya.ticketingservice.service.DepartmentService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/ticketing/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getAll() {
        return departmentService.getAll();
    }

    @PostMapping
    public ResponseEntity<Department> add(@RequestBody AddDepartmentRequestDto department) {
        return departmentService.add(department);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> update(@RequestBody AddDepartmentRequestDto department, @PathVariable(value = "id") int id) {
        return departmentService.update(department, id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id") int id) {
        return departmentService.delete(id);
    }

}
