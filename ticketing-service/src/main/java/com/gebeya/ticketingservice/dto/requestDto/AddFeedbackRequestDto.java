package com.gebeya.ticketingservice.dto.requestDto;

import com.gebeya.ticketingservice.model.Customer;
import com.gebeya.ticketingservice.model.Services;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class AddFeedbackRequestDto {
    private String subject;
    private String body;
    private String first_name;
    private String last_name;
    private String email;
    private int phone_number;

    private int service_id;
}
