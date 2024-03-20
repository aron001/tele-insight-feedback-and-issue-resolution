package com.gebeya.ticketingservice.dto.requestDto;

import lombok.Data;

@Data
public class RateTicketRequestDto {
    private int ticket_id;
    private int rate;
    private String comment;
}
