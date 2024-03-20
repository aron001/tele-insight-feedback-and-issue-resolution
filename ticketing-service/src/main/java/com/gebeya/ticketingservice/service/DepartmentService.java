package com.gebeya.ticketingservice.service;

import com.gebeya.ticketingservice.dto.requestDto.AddDepartmentRequestDto;
import com.gebeya.ticketingservice.model.Department;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DepartmentService {
    ResponseEntity<List<Department>> getAll();
    ResponseEntity<Department> add(AddDepartmentRequestDto department);
    ResponseEntity<Department> update(AddDepartmentRequestDto department, int id);
    boolean delete(int id);
    boolean isIdExisted(int id);
    Department getById(int id);
}
