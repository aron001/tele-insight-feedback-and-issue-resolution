package com.gebeya.ticketingservice.dto.requestDto;

import lombok.Data;

@Data
public class AssignTicketRequestDto {

    private int ticket_id;
    private int engineer_id;
}
