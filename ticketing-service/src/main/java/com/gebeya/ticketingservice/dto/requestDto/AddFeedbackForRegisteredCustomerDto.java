package com.gebeya.ticketingservice.dto.requestDto;

import lombok.Data;

@Data
public class AddFeedbackForRegisteredCustomerDto {
    private String subject;
    private String body;
    private int service_id;

}
