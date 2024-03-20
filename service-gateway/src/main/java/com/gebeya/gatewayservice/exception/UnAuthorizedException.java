package com.gebeya.gatewayservice.exception;


public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(String message){
    super(message);}
}
