package com.gebeya.ticketingservice.dto;

import com.gebeya.ticketingservice.model.Ticket;
import lombok.Data;

import java.util.Date;

@Data
public class AssignedTicketDto {

    private Ticket ticket;
    private int id;
    private String sr_number;
    private String customer_name;
    private String problem_description;
    private String problem_summery;
    private String status;
    private String ticket_owner;
    private float total_hours;
    private Date last_update;
    private String department;
    private String location;
}
