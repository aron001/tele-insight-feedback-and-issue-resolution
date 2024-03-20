package com.gebeya.gatewayservice.exception;

public class HeaderNotFoundException extends RuntimeException {

    public HeaderNotFoundException(String message){
        super(message);
    }
}
