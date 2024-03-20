package com.gebeya.ticketingservice.dto.requestDto;

import org.springframework.stereotype.Service;

@Service
public class UpdateTicketDto {
    private String problem_summary;
    private String problem_description;
    private int customer_id;
    private int services_id;
    private int severity_id;
    private int issue_type_id;
}
