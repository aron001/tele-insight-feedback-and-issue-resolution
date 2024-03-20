package com.gebeya.ticketingservice.controller;

import com.gebeya.ticketingservice.dto.requestDto.AddFeedbackForRegisteredCustomerDto;
import com.gebeya.ticketingservice.dto.requestDto.AddFeedbackRequestDto;
import com.gebeya.ticketingservice.model.Feedback;
import com.gebeya.ticketingservice.service.FeedbackService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/ticketing/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<List<Feedback>> getAll() {
        return feedbackService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getById(@PathVariable(value = "id") int id){
        return feedbackService.getById(id);
    }

    @PostMapping("/unauthenticated")
    public ResponseEntity<Feedback> add(AddFeedbackRequestDto feedback) {
        return feedbackService.add(feedback);
    }
    @PostMapping("/customerFeedback")
    public ResponseEntity<Feedback> addForRegisteredCustomer(AddFeedbackForRegisteredCustomerDto feedback) {
        return feedbackService.addForRegisteredCustomer(feedback);
    }

}
