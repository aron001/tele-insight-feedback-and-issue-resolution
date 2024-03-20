package com.gebeya.ticketingservice.service.impl;

import com.gebeya.ticketingservice.dto.requestDto.AddDepartmentRequestDto;
import com.gebeya.ticketingservice.model.Department;
import com.gebeya.ticketingservice.repository.DepartmentRepository;
import com.gebeya.ticketingservice.service.DepartmentService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public ResponseEntity<List<Department>> getAll() {
        return new ResponseEntity<>(departmentRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Department> add(AddDepartmentRequestDto departmentDto) {
        if (isExisted(departmentDto)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Department is already registered");
        }
        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setDescription(departmentDto.getDescription());
        department.setCreated_By(getUsername()); //will do after authentication
        department.setCreated_date(new Date().toInstant());
        department.setIs_active(true);
        Department savedDepartment = save(department);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    public ResponseEntity<Department> update(AddDepartmentRequestDto departmentDto, int id) {
        if (isIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Id is not found");
        }
        Optional<Department> optionalDepartment = departmentRepository.findById((long) id);
        if (optionalDepartment.isPresent()) {
            Department existedDepartment = optionalDepartment.get();

            if (!departmentDto.getName().isEmpty()) {
                existedDepartment.setName(departmentDto.getName());
            }
            if (!departmentDto.getDescription().isEmpty()) {
                existedDepartment.setDescription(departmentDto.getDescription());
            }
            existedDepartment.setUpdated_at(new Date().toInstant());
            existedDepartment.setUpdated_by(getUsername()); //will change to the actual user
            existedDepartment = save(existedDepartment);

            return new ResponseEntity<>(existedDepartment, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Id not found");
    }

    public boolean delete(int id) {
        if (isIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Id is not found");
        }
        departmentRepository.deleteById((long) id);
        return true;
    }

    private Department save(Department department) {
        return departmentRepository.save(department);
    }

    private boolean isExisted(AddDepartmentRequestDto department) {
        List<Department> departmentList = departmentRepository.findAll();
        boolean isExisted = false;
        for (Department department1 : departmentList) {
            if (department1.getName().equalsIgnoreCase(department.getName())) {
                isExisted = true;
                break;
            }
        }
        return isExisted;
    }

    public boolean isIdExisted(int id) {
        return !departmentRepository.existsById((long) id);
    }

    public Department getById(int id) {
        Optional<Department> optionalDepartment =  departmentRepository.findById((long) id);
        return optionalDepartment.orElse(null);
    }
    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
