package com.gebeya.ticketingservice.enums;

import lombok.Getter;

@Getter
public enum ServiceType {
    INTERNET("Internet Service"),
    MOBILE("Mobile Service"),
    LANDLINE("Landline Service"),
    VOIP("VoIP Service"),
    CLOUD("Cloud Service"),
    DATA("Data Service"),
    SECURITY("Security Service");

    private final String description;

    ServiceType(String description) {
        this.description = description;
    }
}
