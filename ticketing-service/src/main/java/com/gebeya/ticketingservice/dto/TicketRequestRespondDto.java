package com.gebeya.ticketingservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketRequestRespondDto {
    private String sr_number;
    private String problem_summery;
    private String service;
    private String severity;
    private String ticket_status;
    private LocalDateTime last_update;
    private String remark;
}
