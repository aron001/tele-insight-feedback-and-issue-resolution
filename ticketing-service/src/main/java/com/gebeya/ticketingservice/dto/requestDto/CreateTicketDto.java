package com.gebeya.ticketingservice.dto.requestDto;

import lombok.Data;


@Data
public class CreateTicketDto {
    private String problem_summary;
    private String problem_description;
    private int customer_id;
    private int services_id;
    private int severity_id;
    private int issue_type_id;
}
