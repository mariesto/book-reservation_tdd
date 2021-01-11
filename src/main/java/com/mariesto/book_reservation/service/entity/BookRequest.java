package com.mariesto.book_reservation.service.entity;

import lombok.*;

@Data
public class BookRequest {

    private String ISBN;
    private String title;
    private String author;
    private String publishedDate;
    private String status;

}
