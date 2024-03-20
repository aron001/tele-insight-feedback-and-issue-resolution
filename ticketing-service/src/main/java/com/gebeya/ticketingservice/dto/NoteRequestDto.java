package com.gebeya.ticketingservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.gebeya.ticketingservice.model.EngineerServiceTicket;
import com.gebeya.ticketingservice.model.Ticket;
import com.gebeya.ticketingservice.model.WorkType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalTime;

@Data
public class NoteRequestDto {
    private String note;
    private Boolean discussion;
    private Boolean internal;
    private Boolean resolved;
    private String start_time;
    private String end_time;
    private double actual_hours;
    private int engineerServiceTicketId;
    private int workTypeId;
    private int ticketId;
}

