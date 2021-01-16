package com.mariesto.book_reservation.service.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookListResponse {

    private int statusCode;
    private List<BookResponse> books;

}
