package com.gebeya.ticketingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manager extends Person {
    @OneToOne
    private Address address;
    @OneToMany(mappedBy = "manager")
    private Set<Engineer> engineers = new HashSet<>();
}
