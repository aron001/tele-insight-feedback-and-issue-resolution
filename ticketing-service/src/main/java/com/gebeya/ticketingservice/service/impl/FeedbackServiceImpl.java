package com.gebeya.ticketingservice.service.impl;

import com.gebeya.ticketingservice.dto.requestDto.AddFeedbackForRegisteredCustomerDto;
import com.gebeya.ticketingservice.dto.requestDto.AddFeedbackRequestDto;
import com.gebeya.ticketingservice.model.Customer;
import com.gebeya.ticketingservice.model.Feedback;
import com.gebeya.ticketingservice.repository.FeedbackRepository;
import com.gebeya.ticketingservice.service.CustomerService;
import com.gebeya.ticketingservice.service.FeedbackService;
import com.gebeya.ticketingservice.service.ServicesService;
import jakarta.servlet.http.HttpServletRequest;
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
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ServicesService servicesService;
    private final CustomerService customerService;

    public ResponseEntity<List<Feedback>> getAll() {
        return new ResponseEntity<>(feedbackRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Feedback> add(AddFeedbackRequestDto feedbackRequestDto) {
        Feedback feedback = new Feedback();
        if (!servicesService.isIdExisted(feedbackRequestDto.getService_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service Id is not found");
        }
        feedback.setFirst_name(feedbackRequestDto.getFirst_name());
        feedback.setLast_name(feedbackRequestDto.getLast_name());
        feedback.setEmail(feedbackRequestDto.getEmail());
        feedback.getService().setId((long) feedbackRequestDto.getService_id());
        feedback.setSubject(feedbackRequestDto.getSubject());
        feedback.setBody(feedbackRequestDto.getBody());
        feedback.setPhone_number(feedbackRequestDto.getPhone_number());
        String createdBy = feedbackRequestDto.getFirst_name() + " " + feedbackRequestDto.getLast_name();
        feedback.setCreated_By(createdBy);//will do later
        feedback.setCreated_date(new Date().toInstant());

        /*Notification service and more logics will add here
         *
         *
         *
         *
         * */


        return new ResponseEntity<>(feedbackRepository.save(feedback), HttpStatus.CREATED);
    }

    public ResponseEntity<Feedback> addForRegisteredCustomer( AddFeedbackForRegisteredCustomerDto dto) {
        Feedback feedback = new Feedback();
        if (!servicesService.isIdExisted(dto.getService_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service Id is not found");
        }
        feedback.setCustomer(customerService.getByCustomerId(Integer.parseInt(getUsername())));

        feedback.setFirst_name(feedback.getCustomer().getFirst_name());
        feedback.setLast_name(feedback.getCustomer().getLast_name());
        feedback.setEmail(feedback.getCustomer().getAddress().getEmail());
        feedback.getService().setId((long) dto.getService_id());
        feedback.setSubject(dto.getSubject());
        feedback.setBody(dto.getBody());
        feedback.setCreated_By(getUsername());//will do later
        feedback.setCreated_date(new Date().toInstant());

        /*Notification service and more logics will add here
         *
         *
         *
         *
         * */


        return new ResponseEntity<>(feedbackRepository.save(feedback), HttpStatus.CREATED);
    }

    public ResponseEntity<Feedback> getById(int id) {
        Optional<Feedback> optionalFeedback = feedbackRepository.findById((long) id);
        return optionalFeedback.map(feedback -> new ResponseEntity<>(feedback, HttpStatus.OK)).orElse(null);
    }
    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
