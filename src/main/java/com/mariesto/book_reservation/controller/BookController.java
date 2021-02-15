package com.mariesto.book_reservation.controller;

import com.mariesto.book_reservation.service.Book;
import com.mariesto.book_reservation.service.entity.BookListResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    private Book book;

    public BookController(Book book) {
        this.book = book;
    }

    @GetMapping(path = "/")
    public BookListResponse listBook(){
        return book.listBook();
    }

}
