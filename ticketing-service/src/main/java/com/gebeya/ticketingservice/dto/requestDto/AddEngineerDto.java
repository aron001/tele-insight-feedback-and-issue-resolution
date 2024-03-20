package com.gebeya.ticketingservice.dto.requestDto;

import lombok.Data;

import java.util.Date;

@Data
public class AddEngineerDto {
    private String first_name;
    private String middle_name;
    private String last_name;
    private String gender;
    private Date date_of_birth;
    private int work_title_id;
    private Boolean is_manager;

    private String country;
    private String city;
    private String subCity;
    private String woreda;
    private String houseNumber;
    private String email;
    private String phoneNumber;
}
