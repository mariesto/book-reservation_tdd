package com.mariesto.book_reservation.controller;

import com.mariesto.book_reservation.common.InvalidRequestException;
import com.mariesto.book_reservation.service.entity.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = InvalidRequestException.class)
    public ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex){
        ServiceResponse response = new ServiceResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setStatusMessage(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
