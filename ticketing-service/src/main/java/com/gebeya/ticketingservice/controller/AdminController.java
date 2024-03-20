package com.gebeya.ticketingservice.controller;

import com.gebeya.ticketingservice.dto.requestDto.AddCustomerDto;
import com.gebeya.ticketingservice.dto.requestDto.AddEngineerDto;
import com.gebeya.ticketingservice.dto.requestDto.AddWorkTitleDto;
import com.gebeya.ticketingservice.model.Customer;
import com.gebeya.ticketingservice.model.Engineer;
import com.gebeya.ticketingservice.model.WorkTitle;
import com.gebeya.ticketingservice.service.CustomerService;
import com.gebeya.ticketingservice.service.EngineerService;
import com.gebeya.ticketingservice.service.WorkTitleService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/ticketing/admin")
public class AdminController {
    private final WorkTitleService workTitleService;
    private final CustomerService customerService;
    private final EngineerService engineerService;

    @GetMapping("/getWorkTitle")
    public ResponseEntity<List<WorkTitle>> getAllWorkTitle() {
        return workTitleService.getAll();
    }

    @GetMapping("/getWorkTitleById/{id}")
    public ResponseEntity<WorkTitle> getWorkTitleById(@PathVariable(value = "id") int id) {
        return workTitleService.getById(id);
    }

    @PostMapping("/addWorkTitle")
    public ResponseEntity<WorkTitle> addWorkTitle(@RequestBody AddWorkTitleDto workTitleDto) {
        return workTitleService.add(workTitleDto);
    }

    @PutMapping("/updateWorkTitle/{id}")
    public ResponseEntity<WorkTitle> updateWorkTitle(@RequestBody AddWorkTitleDto addWorkTitleDto, @PathVariable(value = "id") int id) {
        return workTitleService.update(addWorkTitleDto, id);
    }

    @DeleteMapping("/deleteWorkTitle/{id}")
    public ResponseEntity<Boolean> deleteWorkTitle(@PathVariable(value = "id") int id) {
        return workTitleService.delete(id);
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return customerService.getAll();
    }

    @GetMapping("/getCustomerById/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") int id) {
        return customerService.getById(id);
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> addCustomer(@RequestBody AddCustomerDto addCustomerDto) {
        return customerService.add(addCustomerDto);
    }

    @PutMapping("/updateClient/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody AddCustomerDto dto, @PathVariable(value = "id") int id) {
        return customerService.update(dto, id);
    }

    @GetMapping("/getAllEngineers")
    public ResponseEntity<List<Engineer>> getAllEngineers() {
        return engineerService.getAll();
    }

    @GetMapping("/getEngineerById/{id}")
    public ResponseEntity<Engineer> getEngineerById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(engineerService.getById(id));
    }

    @PostMapping("/addEngineer")
    public ResponseEntity<Engineer> addEngineer(@RequestBody AddEngineerDto engineerDto) {
        return engineerService.add(engineerDto);
    }

    @PutMapping("/updateEngineer/{id}")
    public ResponseEntity<Engineer> updateEngineer(@RequestBody AddEngineerDto engineerDto, @PathVariable(value = "id") int id) {
        return engineerService.update(engineerDto, id);
    }
}