package com.gebeya.ticketingservice.service.impl;

import com.gebeya.ticketingservice.dto.EngineerServiceTicketDto;
import com.gebeya.ticketingservice.dto.requestDto.AssignTicketDepartmentLocationRequestDto;
import com.gebeya.ticketingservice.dto.requestDto.AssignTicketRequestDto;
import com.gebeya.ticketingservice.model.EngineerServiceTicket;
import com.gebeya.ticketingservice.model.Ticket;
import com.gebeya.ticketingservice.model.TicketStatus;
import com.gebeya.ticketingservice.repository.EngineerServiceTicketRepository;
import com.gebeya.ticketingservice.repository.TicketRepository;
import com.gebeya.ticketingservice.service.*;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class EngineerServiceTicketImpl implements EngineerServiceTicketService {

    private final EngineerServiceTicketRepository engineerServiceTicketRepository;
    private final TicketStatusService ticketStatusService;
    private final EngineerService engineerService;
    private final LocationService locationService;
    private final DepartmentService departmentService;

    private final TicketRepository ticketRepository;

    public EngineerServiceTicketDto assignTicket(AssignTicketRequestDto dto) {

        EngineerServiceTicket engineerServiceTicket = new EngineerServiceTicket();
        engineerServiceTicket.setTicketStatus(getTicketStatusByName());
        engineerServiceTicket.setEngineer(engineerService.getById(dto.getEngineer_id()));
        engineerServiceTicket.setTicket(getById(dto.getTicket_id()));
        engineerServiceTicket.setIs_active(true);
        engineerServiceTicket.setCreated_date(new Date().toInstant());
        //engineerServiceTicket.setCreated_By(engineerService.getById(createdBy).getFirst_name() + " " + engineerService.getById(createdBy).getLast_name());
        engineerServiceTicket.setCreated_By("admin"); // will be changed in authentication
        //ticket table status should be updated and write on remark column that engineer is assigned
        update(engineerServiceTicket.getTicket());
        EngineerServiceTicket savedTicket = engineerServiceTicketRepository.save(engineerServiceTicket);
        // will call notification service for updating customer
        return getServiceTicketDto(savedTicket);
    }

    public ResponseEntity<List<EngineerServiceTicket>> getAll() {
        return new ResponseEntity<>(engineerServiceTicketRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<EngineerServiceTicket> getByESTId(int id) {
        //EST is Engineer Service Ticket
        Optional<EngineerServiceTicket> optional = engineerServiceTicketRepository.findById((long) id);
        return optional.map(ticket -> new ResponseEntity<>(ticket, HttpStatus.OK)).orElse(null);

    }

    public EngineerServiceTicket getByESTId2(int id) {
        //EST is Engineer Service Ticket
        Optional<EngineerServiceTicket> optional = engineerServiceTicketRepository.findById((long) id);
        return optional.orElse(null);
    }


    private EngineerServiceTicketDto getServiceTicketDto(EngineerServiceTicket ticket) {
        EngineerServiceTicketDto dto = new EngineerServiceTicketDto();

        dto.setId(Math.toIntExact(ticket.getTicket().getId()));
        dto.setTicket_owner(ticket.getEngineer().getFirst_name() + " " + ticket.getEngineer().getLast_name());
        dto.setProblem_description(ticket.getTicket().getProblem_description());
        dto.setProblem_summery(ticket.getTicket().getProblem_summary());
        dto.setSr_number(ticket.getTicket().getSr_number());
        return dto;
    }

    public EngineerServiceTicket assignLocationAndDepartment(AssignTicketDepartmentLocationRequestDto dto) {
        checkLocationAndDepartmentRequirement(dto);
        Optional<EngineerServiceTicket> optional = engineerServiceTicketRepository.findById((long) dto.getEngineer_ticket_id());
        if (optional.isPresent()) {
            EngineerServiceTicket ticket = optional.get();
            ticket.setDepartment(departmentService.getById(dto.getDepartment_id()));
            ticket.setLocation(locationService.getById(dto.getLocation_id()));
            return engineerServiceTicketRepository.save(ticket);
        }
        throw new RuntimeException("Engineer Ticket Id is not found");
    }

    public ResponseEntity<EngineerServiceTicket> getEngineerTicketById(int engineer_id) {
        if (!engineerService.isEngineerExisted(engineer_id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Engineer Id Not Found.");
        }
        EngineerServiceTicket ticket = new EngineerServiceTicket();
        ticket.getEngineer().setId((long) engineer_id);
        Optional<EngineerServiceTicket> optional = engineerServiceTicketRepository.findById(ticket.getEngineer().getId());
        if (optional.isPresent())
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Engineer Service Ticket is not found");
    }

    private void checkLocationAndDepartmentRequirement(AssignTicketDepartmentLocationRequestDto dto) {
        if (isLocationIdExisted(dto.getLocation_id())) {
            throw new RuntimeException("Location Id not found");
        }
        if (isDepartmentIdExisted(dto.getDepartment_id())) {
            throw new RuntimeException("Department Id is not found");
        }
        if (isTicketIdExisted(dto.getEngineer_ticket_id())) {
            throw new RuntimeException("Engineer Ticket Id is not found");
        }
    }

    private boolean isLocationIdExisted(int id) {
        return locationService.isIdExisted(id);
    }

    private boolean isDepartmentIdExisted(int id) {
        return departmentService.isIdExisted(id);
    }


    private TicketStatus getTicketStatusByName() {
        return ticketStatusService.getTicketStatusByName("assigned");

    }

    public Ticket getById(int id) {
        Optional<Ticket> optionalTicket = ticketRepository.findById((long) id);
        return optionalTicket.orElse(null);
    }

    public Ticket update(Ticket ticket) {
        if (isTicketIdExisted(Math.toIntExact(ticket.getId()))) {
            throw new RuntimeException("Ticket not found");// ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Ticket existedTicket = getById(Math.toIntExact(ticket.getId()));
        if (ticket.getTicketStatus().getId() != null) {
            existedTicket.setTicketStatus(ticket.getTicketStatus());
        }
        if (ticket.getRemark() != null) {
            existedTicket.setRemark(ticket.getRemark());
        }

        return ticketRepository.save(existedTicket);
    }


    public boolean isTicketIdExisted(int id) {
        return !engineerServiceTicketRepository.existsById((long) id);
    }
}
