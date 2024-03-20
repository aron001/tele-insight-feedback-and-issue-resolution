package com.gebeya.ticketingservice.service;

import com.gebeya.ticketingservice.dto.requestDto.AddFeedbackForRegisteredCustomerDto;
import com.gebeya.ticketingservice.dto.requestDto.AddFeedbackRequestDto;
import com.gebeya.ticketingservice.model.Feedback;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FeedbackService {
    ResponseEntity<List<Feedback>> getAll();

    ResponseEntity<Feedback> add(AddFeedbackRequestDto feedbackRequestDto);

    ResponseEntity<Feedback> addForRegisteredCustomer(AddFeedbackForRegisteredCustomerDto dto);

    ResponseEntity<Feedback> getById(int id);
}
