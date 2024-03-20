package com.gebeya.ticketingservice.service.impl;

import com.gebeya.ticketingservice.dto.AssignedTicketDto;
import com.gebeya.ticketingservice.dto.EngineerServiceTicketDto;
import com.gebeya.ticketingservice.dto.requestDto.AssignTicketRequestDto;
import com.gebeya.ticketingservice.model.Ticket;
import com.gebeya.ticketingservice.repository.TicketRepository;
import com.gebeya.ticketingservice.service.EngineerService;
import com.gebeya.ticketingservice.service.EngineerServiceTicketService;
import com.gebeya.ticketingservice.service.TicketService;
import com.gebeya.ticketingservice.service.TicketStatusService;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final EngineerServiceTicketService engineerServiceTicketService;
    private final EngineerService engineerService;
    private final TicketStatusService ticketStatusService;

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Optional<Ticket> findTicketBySrNumber(String srNumber) {
        return ticketRepository.findBySrNumber(srNumber);
    }

    public List<Ticket> findAllTicketsByCustomerId(int id) {
        List<Ticket> customerTickets = ticketRepository.findAll();

        List<Ticket> filteredTickets = new ArrayList<>();
        for (Ticket ticket : customerTickets) {
            if (ticket.getCustomer().getId() == id) {
                filteredTickets.add(ticket);
            }
        }
        return filteredTickets;
    }

    public List<Ticket> findUnResolvedTicketsByCustomerId(int id) {
        List<Ticket> customerTickets = ticketRepository.findAll();

        List<Ticket> filteredTickets = new ArrayList<>();
        for (Ticket ticket : customerTickets) {
            if (ticket.getCustomer().getId() == id) {
                if (!ticket.getTicketStatus().getName().equalsIgnoreCase("closed")) {
                    filteredTickets.add(ticket);
                }
            }
        }
        return filteredTickets;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public boolean isTicketIdExisted(int id) {
        return ticketRepository.existsById((long) id);
    }

    public AssignedTicketDto assignTicket(int engineerId, int ticketId) {
        if (!checkEngineer(engineerId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Engineer ID is not found");
        }
        if (!checkTicket(ticketId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket ID is not found");
        }
        // assigner id should change at authorization.
        AssignTicketRequestDto dto = new AssignTicketRequestDto();
        dto.setTicket_id(ticketId);
        dto.setEngineer_id(engineerId);
        EngineerServiceTicketDto engineerServiceTicketDto = engineerServiceTicketService.assignTicket(dto);

        AssignedTicketDto assignedTicketDto = setEngineerTicketDto(engineerServiceTicketDto);

        assignedTicketDto.setTicket(getById(engineerServiceTicketDto.getId()));
        assignedTicketDto.getTicket().setTicketStatus(ticketStatusService.getTicketStatusByName("assigned"));
        assignedTicketDto.getTicket().setRemark("Engineer Assigned.");
        update(assignedTicketDto.getTicket());
        return assignedTicketDto;
    }
    private AssignedTicketDto setEngineerTicketDto(EngineerServiceTicketDto dto){
        AssignedTicketDto ticketDto = new AssignedTicketDto();
        ticketDto.setId(dto.getId());
        ticketDto.setTicket_owner(dto.getTicket_owner());
        ticketDto.setSr_number(dto.getSr_number());
        ticketDto.setProblem_summery(dto.getProblem_summery());
        ticketDto.setProblem_description(dto.getProblem_description());
        ticketDto.setDepartment(dto.getDepartment());
        ticketDto.setStatus(dto.getStatus());
        ticketDto.setLocation(dto.getLocation());
        ticketDto.setCustomer_name(dto.getCustomer_name());
        return ticketDto;

    }
    boolean checkEngineer(int engineerId) {
        return engineerService.isEngineerExisted(engineerId);
    }

    boolean checkTicket(int ticketId) {
        return isTicketIdExisted(ticketId);
    }

    public Ticket getById(int id) {
        Optional<Ticket>  optionalTicket = ticketRepository.findById((long) id);
        return optionalTicket.orElse(null);
    }

    public Ticket update(Ticket ticket) {
        if (!isTicketIdExisted(Math.toIntExact(ticket.getId()))) {
            throw new RuntimeException("Ticket not found");// ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Ticket existedTicket = getById(Math.toIntExact(ticket.getId()));
        if (ticket.getTicketStatus().getId() != null) {
            existedTicket.setTicketStatus(ticket.getTicketStatus());
        }
        if (ticket.getRemark() != null) {
            existedTicket.setRemark(ticket.getRemark());
        }

        existedTicket.setLast_update(LocalDateTime.now());
        return ticketRepository.save(existedTicket);
    }
}
