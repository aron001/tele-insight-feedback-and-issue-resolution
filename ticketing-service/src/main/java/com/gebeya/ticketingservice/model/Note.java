package com.gebeya.ticketingservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.gebeya.ticketingservice.enums.NoteType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Note extends BaseModel {
    private String note;
    private Boolean discussion;
    private Boolean internal;
    private Boolean resolved;
    private LocalTime start_time;
    private LocalTime end_time;
    private double actual_hours;

    @ManyToOne
    private EngineerServiceTicket engineerServiceTicket;

    @ManyToOne
    private WorkType workType;

    @ManyToOne
    private Ticket ticket;
}
