package com.gebeya.ticketingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EngineerServiceTicket extends BaseModel{

    @ManyToOne
    private Ticket ticket;

    @ManyToOne
    private Engineer engineer;
    @ManyToOne
    private TicketStatus ticketStatus;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Location location;

}
