package com.mariesto.book_reservation.service.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BookRequest {

    private String ISBN;
    private String title;
    private String author;
    private String publishedDate;
    private String status;

}
