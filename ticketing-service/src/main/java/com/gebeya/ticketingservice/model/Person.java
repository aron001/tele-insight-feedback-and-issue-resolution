package com.gebeya.ticketingservice.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class Person extends BaseModel {
    private String first_name;
    private String middle_name;
    private String last_name;
    private String gender;
    private Date date_of_birth;

}
