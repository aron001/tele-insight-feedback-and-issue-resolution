package com.gebeya.ticketingservice.service.impl;

import com.gebeya.ticketingservice.dto.TicketRequestRespondDto;
import com.gebeya.ticketingservice.dto.requestDto.AddCustomerDto;
import com.gebeya.ticketingservice.dto.requestDto.CreateTicketDto;
import com.gebeya.ticketingservice.dto.requestDto.GetCustomerTicketDto;
import com.gebeya.ticketingservice.enums.Authority;
import com.gebeya.ticketingservice.model.Address;
import com.gebeya.ticketingservice.model.Customer;
import com.gebeya.ticketingservice.model.Ticket;
import com.gebeya.ticketingservice.repository.CustomerRepository;
import com.gebeya.ticketingservice.service.*;
import com.gebeya.ticketingservice.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Data
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final SeverityService severityService;
    private final ServicesService servicesService;
    private final TicketStatusService ticketStatusService;
    private final IssueTypeService issueTypeService;
    private final TicketService ticketService;
    private final AddressService addressService;
    private final UserUtil userUtil;

    @Transactional
    public ResponseEntity<Customer> add(AddCustomerDto addCustomerDto) {
        Customer customer = new Customer();
        customer.setFirst_name(addCustomerDto.getFirst_name());
        customer.setMiddle_name(addCustomerDto.getMiddle_name());
        customer.setLast_name(addCustomerDto.getLast_name());
        customer.setGender(addCustomerDto.getGender());
        customer.setDate_of_birth(addCustomerDto.getDate_of_birth());
        customer.setAddress(assignAddress(addCustomerDto));
        if (isCustomerExisted(customer)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already Registered");
        }
        customer.getAddress().setCreated_date(new Date().toInstant());
        Address address = addressService.add(customer.getAddress());
        customer.setCreated_date(new Date().toInstant());
        customer.setIs_active(true);
        customer.setCreated_By(getUsername());
        customer.setAddress(addressService.getById(Math.toIntExact(address.getId())));

        Customer saved_customer = save(customer);
        userUtil.createUser(saved_customer.getFirst_name(),saved_customer.getMiddle_name(), Math.toIntExact(saved_customer.getId()), Authority.CUSTOMER,saved_customer.getAddress().getEmail());

        return new ResponseEntity<>(saved_customer, HttpStatus.CREATED);
    }
    private Address assignAddress(AddCustomerDto dto){
        Address address = new Address();
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());
        address.setEmail(dto.getEmail());
        address.setSubCity(dto.getSubCity());
        address.setWoreda(dto.getWoreda());
        address.setPhoneNumber(dto.getPhoneNumber());
        address.setHouseNumber(dto.getHouseNumber());
        return address;
    }
    public ResponseEntity<List<Customer>> getAll() {
        return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Customer> getById(int id) {
        Optional<Customer> optionalCustomer = customerRepository.findById((long) id);
        if (optionalCustomer.isPresent()) {
            Customer existedCustomer = optionalCustomer.get();
            return new ResponseEntity<>(existedCustomer, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
    }

    public Customer getByCustomerId(int id) {
        Optional<Customer> optionalCustomer = customerRepository.findById((long) id);
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get();

        }
        throw new RuntimeException("Customer Id is not found");
    }

    @Transactional
    public ResponseEntity<Customer> update(AddCustomerDto customer, int id) {
        if (!isCustomerIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client ID is not found");
        }
        Optional<Customer> optionalCustomer = customerRepository.findById((long) id);
        if (optionalCustomer.isPresent()) {
            Customer existedClient = optionalCustomer.get();
            if (!customer.getFirst_name().isEmpty()) {
                existedClient.setFirst_name(customer.getFirst_name());
            }
            if (!customer.getMiddle_name().isEmpty()) {
                existedClient.setMiddle_name(customer.getMiddle_name());
            }
            if (!customer.getLast_name().isEmpty()) {
                existedClient.setLast_name(customer.getLast_name());
            }
            if (!customer.getDate_of_birth().toString().isEmpty()) {
                existedClient.setDate_of_birth(customer.getDate_of_birth());
            }
            if (!customer.getGender().isEmpty()) {
                existedClient.setGender(customer.getGender());
            }
            if (!customer.getCity().isEmpty()) {
                existedClient.getAddress().setCity(customer.getCity());
            }
            if (!customer.getSubCity().isEmpty()) {
                existedClient.getAddress().setSubCity(customer.getSubCity());
            }
            if (!customer.getEmail().isEmpty()) {
                existedClient.getAddress().setEmail(customer.getEmail());
            }
            if (!customer.getHouseNumber().isEmpty()) {
                existedClient.getAddress().setHouseNumber(customer.getHouseNumber());
            }
            if (!customer.getPhoneNumber().isEmpty()) {
                existedClient.getAddress().setPhoneNumber(customer.getPhoneNumber());
            }
            if (!customer.getWoreda().isEmpty()) {
                existedClient.getAddress().setWoreda(customer.getWoreda());
            }
            existedClient.setUpdated_by(getUsername());
            existedClient.setUpdated_at(new Date().toInstant());
            Customer updatedClient = save(existedClient);

            return new ResponseEntity<>(updatedClient, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Id is not found");
    }

    private boolean isCustomerIdExisted(int id) {
        return customerRepository.existsById((long) id);
    }

    @Transactional
    public ResponseEntity<TicketRequestRespondDto> createTicket(CreateTicketDto createTicketDto) {
        checkRequirements(createTicketDto);
        Ticket ticket = new Ticket();
        ticket.setCustomer(getByCustomerId(Integer.parseInt(getUsername())));
        ticket.setServices(servicesService.getServiceById(createTicketDto.getServices_id()));
        ticket.setSeverity(severityService.getSeverityById(createTicketDto.getSeverity_id()));
        ticket.setIssueType(issueTypeService.getIssueTypeById(createTicketDto.getServices_id()));

        ticket.setProblem_description(createTicketDto.getProblem_description());
        ticket.setProblem_summary(createTicketDto.getProblem_summary());

        ticket.setSr_number(generateSRNumber());
        ticket.setTicketStatus(ticketStatusService.getTicketStatusByName("new"));
        ticket.setCreated_By(getUsername());
        ticket.setCreated_date(new Date().toInstant());
        ticket.setIs_active(true);
        Ticket savedTicket = ticketService.createTicket(ticket);
        TicketRequestRespondDto ticketRequestRespondDto = getTicketRespondDto(savedTicket);
        // will call notification service
        return new ResponseEntity<>(ticketRequestRespondDto, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<TicketRequestRespondDto> updateTicket(CreateTicketDto updateTicketDto, int id) {
        if (!ticketService.isTicketIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket Not found");
        }
        Optional<Ticket> optionalTicket = Optional.ofNullable(ticketService.getById(id));
        if (optionalTicket.isPresent()) {
            checkRequirements(updateTicketDto);
            Ticket existedTicket = optionalTicket.get();
            if (updateTicketDto.getServices_id() != 0) {
                existedTicket.getServices().setId((long) updateTicketDto.getServices_id());
            }
            if (updateTicketDto.getSeverity_id() != 0) {
                existedTicket.getSeverity().setId((long) updateTicketDto.getSeverity_id());
            }
            if (updateTicketDto.getIssue_type_id() != 0) {
                existedTicket.getIssueType().setId((long) updateTicketDto.getIssue_type_id());
            }
            if (!updateTicketDto.getProblem_summary().isEmpty()) {
                existedTicket.setProblem_description(updateTicketDto.getProblem_description());
            }
            if (!updateTicketDto.getProblem_description().isEmpty()) {
                existedTicket.setProblem_summary(updateTicketDto.getProblem_summary());
            }

            existedTicket.setUpdated_by(getUsername());
            existedTicket.setUpdated_at(new Date().toInstant());
            Ticket savedTicket = ticketService.createTicket(existedTicket);
            savedTicket.setServices(servicesService.getServiceById(Math.toIntExact(savedTicket.getServices().getId())));
            savedTicket.setSeverity(severityService.getSeverityById(Math.toIntExact(savedTicket.getSeverity().getId())));
            savedTicket.setTicketStatus(ticketStatusService.getTicketStatusById(Math.toIntExact(savedTicket.getTicketStatus().getId())));
            savedTicket.setIssueType(issueTypeService.getIssueTypeById(Math.toIntExact(savedTicket.getIssueType().getId())));
            TicketRequestRespondDto ticketRequestRespondDto = getTicketRespondDto(savedTicket);

            return new ResponseEntity<>(ticketRequestRespondDto, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Ticket Id is not found");
    }

    public ResponseEntity<List<TicketRequestRespondDto>> getCustomerUnresolvedTicket(GetCustomerTicketDto dto) {
        List<Ticket> ticketList = ticketService.findUnResolvedTicketsByCustomerId(Integer.parseInt(getUsername()));
        List<TicketRequestRespondDto> ticketRequestRespondDto = getTicketListRespondDto(ticketList);
        return new ResponseEntity<>(ticketRequestRespondDto, HttpStatus.OK);
    }

    private static List<TicketRequestRespondDto> getTicketListRespondDto(List<Ticket> ticketList) {

        List<TicketRequestRespondDto> ticketRequestRespondDtoList = new ArrayList<>();

        for (Ticket savedTicket : ticketList) {
            TicketRequestRespondDto ticketRequestRespondDto = new TicketRequestRespondDto();
            ticketRequestRespondDto.setTicket_status(savedTicket.getTicketStatus().getName());
            ticketRequestRespondDto.setService(savedTicket.getServices().getName());
            ticketRequestRespondDto.setSeverity(savedTicket.getSeverity().getName());
            ticketRequestRespondDto.setSr_number(savedTicket.getSr_number());
            ticketRequestRespondDto.setProblem_summery(savedTicket.getProblem_summary());
            ticketRequestRespondDto.setLast_update(savedTicket.getLast_update());
            ticketRequestRespondDto.setRemark(savedTicket.getRemark());
            ticketRequestRespondDtoList.add(ticketRequestRespondDto);
        }

        return ticketRequestRespondDtoList;
    }

    public ResponseEntity<List<TicketRequestRespondDto>> getAllCustomerTicket() {
        List<Ticket> ticketList = ticketService.findAllTicketsByCustomerId(Integer.parseInt(getUsername()));
        List<TicketRequestRespondDto> ticketRequestRespondDto = getTicketListRespondDto(ticketList);
        return new ResponseEntity<>(ticketRequestRespondDto, HttpStatus.OK);
    }

    public void checkRequirements(CreateTicketDto ticket) {

        if (servicesService.isIdExisted(Math.toIntExact(ticket.getServices_id()))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service Id is not found");
        }
        if (severityService.isIdExisted(Math.toIntExact(ticket.getSeverity_id()))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Severity Id is not found");
        }
        if (issueTypeService.isIdExisted(Math.toIntExact(ticket.getIssue_type_id()))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue Type Id is not found");
        }
    }

    private static String generateSRNumber() {
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        int firstDigit = random.nextInt(9) + 1;
        sb.append(firstDigit).append("-");

        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return sb.toString();
    }

    private static TicketRequestRespondDto getTicketRespondDto(Ticket savedTicket) {
        TicketRequestRespondDto ticketRequestRespondDto = new TicketRequestRespondDto();
        ticketRequestRespondDto.setTicket_status(savedTicket.getTicketStatus().getName());
        ticketRequestRespondDto.setService(savedTicket.getServices().getName());
        ticketRequestRespondDto.setSeverity(savedTicket.getSeverity().getName());
        ticketRequestRespondDto.setSr_number(savedTicket.getSr_number());
        ticketRequestRespondDto.setProblem_summery(savedTicket.getProblem_summary());
        ticketRequestRespondDto.setLast_update(savedTicket.getLast_update());
        ticketRequestRespondDto.setRemark(savedTicket.getRemark());
        return ticketRequestRespondDto;
    }

    private boolean isCustomerExisted(Customer client) {
        List<Customer> customerList = customerRepository.findAll();
        boolean isExisted = false;
        for (Customer existedCustomer : customerList) {
            if (existedCustomer.getFirst_name().equalsIgnoreCase(client.getFirst_name()) &&
                    existedCustomer.getMiddle_name().equalsIgnoreCase(client.getMiddle_name()) &&
                    existedCustomer.getLast_name().equalsIgnoreCase(client.getLast_name())) {
                isExisted = true;
                break;
            }
        }
        return isExisted;
    }

    public Customer save(Customer client) {
        return customerRepository.save(client);
    }

    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
