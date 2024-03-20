package com.gebeya.ticketingservice.dto.requestDto;

import lombok.Data;

@Data
public class AssignTicketDepartmentLocationRequestDto {

    private int engineer_ticket_id;
    private int location_id;
    private int department_id;
}
