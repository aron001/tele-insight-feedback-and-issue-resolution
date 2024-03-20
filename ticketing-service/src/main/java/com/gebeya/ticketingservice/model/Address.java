package com.gebeya.ticketingservice.model;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address extends BaseModel {
    private String country;
    private String city;
    private String subCity;
    private String woreda;
    private String houseNumber;
    private String email;
    private String phoneNumber;
}
