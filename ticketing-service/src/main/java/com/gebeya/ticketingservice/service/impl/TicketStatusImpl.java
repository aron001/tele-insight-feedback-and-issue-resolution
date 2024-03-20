package com.gebeya.ticketingservice.service.impl;

import com.gebeya.ticketingservice.dto.requestDto.AddTicketStatusDto;
import com.gebeya.ticketingservice.model.TicketStatus;
import com.gebeya.ticketingservice.repository.TicketStatusRepository;
import com.gebeya.ticketingservice.service.TicketStatusService;
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
public class TicketStatusImpl implements TicketStatusService {
    private final TicketStatusRepository ticketStatusRepository;

    public ResponseEntity<List<TicketStatus>> getAll() {
        return new ResponseEntity<>(ticketStatusRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<TicketStatus> add(AddTicketStatusDto dto) {
        TicketStatus ticketStatus = new TicketStatus();
        ticketStatus.setName(dto.getName());
        ticketStatus.setDescription(dto.getDescription());
        if (isExisted(ticketStatus)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ticket Status is already registered");
        }
        ticketStatus.setCreated_By("admin"); //will do after authentication
        ticketStatus.setCreated_date(new Date().toInstant());
        ticketStatus.setIs_active(true);
        TicketStatus savedTicketStatus = save(ticketStatus);
        return new ResponseEntity<>(savedTicketStatus, HttpStatus.CREATED);
    }

    public ResponseEntity<TicketStatus> update(AddTicketStatusDto  dto, int id) {
        TicketStatus ticketStatus = new TicketStatus();
        ticketStatus.setName(dto.getName());
        ticketStatus.setDescription(dto.getDescription());
        if (isIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket status Id is not found");
        }
        Optional<TicketStatus> optionalTicketStatus = ticketStatusRepository.findById((long) id);
        if (optionalTicketStatus.isPresent()) {
            TicketStatus existedTicketStatus = optionalTicketStatus.get();
            if (!ticketStatus.getName().isEmpty()) {
                existedTicketStatus.setName(ticketStatus.getName());
            }
            if (!ticketStatus.getDescription().isEmpty()) {
                existedTicketStatus.setDescription(ticketStatus.getDescription());
            }
            existedTicketStatus.setUpdated_at(new Date().toInstant());
            existedTicketStatus.setUpdated_by("admin"); //will change to the actual user
            existedTicketStatus = save(existedTicketStatus);

            return new ResponseEntity<>(existedTicketStatus, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket Status is not found");
    }

    public int getTicketStatusId(String name) {
        List<TicketStatus> ticketStatusList = ticketStatusRepository.findAll();
        for (TicketStatus ticketStatus : ticketStatusList) {
            if (ticketStatus.getName().equalsIgnoreCase(name))
                return Math.toIntExact(ticketStatus.getId());
        }
        return 0;
    }

    public String getTicketStatusName(int id) {
        Optional<TicketStatus> optionalTicketStatus = ticketStatusRepository.findById((long) id);
        if (optionalTicketStatus.isPresent()) {
            TicketStatus ticketStatus = optionalTicketStatus.get();
            return ticketStatus.getName();
        }
        return null;
    }

    public TicketStatus getTicketStatusById(int id) {
        Optional<TicketStatus>  optionalTicketStatus = ticketStatusRepository.findById((long) id);
        return optionalTicketStatus.orElse(null);
    }

    public TicketStatus getTicketStatusByName(String name) {
        List<TicketStatus> ticketStatusList = ticketStatusRepository.findAll();
        for (TicketStatus ticketStatus : ticketStatusList) {
            if (ticketStatus.getName().equalsIgnoreCase(name))
                return ticketStatus;
        }
        return null;
    }


    public boolean delete(int id) {
        if (isIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service Id is not found");
        }
        ticketStatusRepository.deleteById((long) id);
        return true;
    }

    private TicketStatus save(TicketStatus ticketStatus) {
        return ticketStatusRepository.save(ticketStatus);
    }

    private boolean isExisted(TicketStatus ticketStatus) {
        List<TicketStatus> ticketStatuses = ticketStatusRepository.findAll();
        boolean isExisted = false;
        for (TicketStatus Status : ticketStatuses) {
            if (Status.getName().equalsIgnoreCase(ticketStatus.getName())) {
                isExisted = true;
                break;
            }
        }
        return isExisted;
    }

    public boolean isIdExisted(int id) {
        return !ticketStatusRepository.existsById((long) id);
    }

}
