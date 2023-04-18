package com.mariesto.book_reservation.service.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookResponse extends ServiceResponse implements Serializable {

    private String ISBN;
    private String title;
    private String author;
    private String publishedDate;
    private String status;

}
