package com.gebeya.ticketingservice.model;

import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TicketStatus extends BaseModel{
    private String name;
    private String description;
}
