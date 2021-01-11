package com.mariesto.book_reservation.service.entity;

import lombok.Data;

@Data
public class BookResponse {

    private String ISBN;
    private String title;
    private String author;
    private String publishedDate;
    private String status;

}
