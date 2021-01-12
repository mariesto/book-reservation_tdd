package com.mariesto.book_reservation.service.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookResponse {

    private String ISBN;
    private String title;
    private String author;
    private String publishedDate;
    private String status;

}
