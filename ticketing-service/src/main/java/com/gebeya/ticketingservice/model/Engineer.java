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
public class Engineer extends Person {
    @ManyToOne
    private Address address;
    @ManyToOne
    private Manager manager;
    @ManyToOne
    private WorkTitle workTitle;
//    @OneToMany(mappedBy = "engineer")
//    private Set<Ticket> tickets = new HashSet<>();
}
