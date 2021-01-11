package com.mariesto.book_reservation.common;

public class InvalidRequestException extends Exception {

    public InvalidRequestException(String message) {
        super(message);
    }
}
