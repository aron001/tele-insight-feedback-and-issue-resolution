package com.gebeya.ticketingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RateTicket extends BaseModel {

    @ManyToOne
    private Ticket ticket;
    private int rate;
    private String comment;
}
