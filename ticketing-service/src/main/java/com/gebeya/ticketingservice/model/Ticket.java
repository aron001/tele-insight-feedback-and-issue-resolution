package com.gebeya.ticketingservice.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Ticket extends BaseModel {

    private String problem_summary;
    private String problem_description;
    private String sr_number;
    private LocalDateTime last_update;
    private String remark;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Services services;
    @ManyToOne
    private Severity severity;
    @ManyToOne
    private IssueType issueType;
    @ManyToOne
    private TicketStatus ticketStatus;

}