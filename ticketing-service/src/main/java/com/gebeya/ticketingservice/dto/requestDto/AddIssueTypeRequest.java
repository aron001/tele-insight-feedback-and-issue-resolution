package com.gebeya.ticketingservice.dto.requestDto;

import lombok.Data;

@Data
public class AddIssueTypeRequest {
    private String name;
    private String description;
}
