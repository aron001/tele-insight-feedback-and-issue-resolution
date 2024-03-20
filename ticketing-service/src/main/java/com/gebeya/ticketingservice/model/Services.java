package com.gebeya.ticketingservice.model;

import jakarta.persistence.Entity;
import lombok.*;
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Services extends BaseModel{

    private String name;
    private String description;
}
