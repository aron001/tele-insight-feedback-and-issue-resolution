package com.gebeya.ticketingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Feedback extends BaseModel {
    private String subject;
    private String body;
    private String first_name;
    private String last_name;
    private String email;
    private int phone_number;

    @ManyToOne
    private Services service;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Ticket ticket;

}
