package com.gebeya.gatewayservice.exception;

import com.gebeya.gatewayservice.dto.ErrorMessageDTO;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HeaderNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleHeaderNotFoundException(HeaderNotFoundException exception) {
        ErrorMessageDTO errorMessageDTO = ErrorMessageDTO.builder().message(exception.getMessage()).build();
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorMessageDTO> handleUnAuthorizedException(HeaderNotFoundException exception) {
        ErrorMessageDTO errorMessageDTO = ErrorMessageDTO.builder().message(exception.getMessage()).build();
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.UNAUTHORIZED);
    }

}
