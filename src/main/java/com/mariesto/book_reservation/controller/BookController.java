package com.mariesto.book_reservation.controller;

import com.mariesto.book_reservation.common.InvalidRequestException;
import com.mariesto.book_reservation.service.Book;
import com.mariesto.book_reservation.service.entity.BookListResponse;
import com.mariesto.book_reservation.service.entity.BookRequest;
import com.mariesto.book_reservation.service.entity.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/")
    public ResponseEntity<Object> createBook(@RequestBody BookRequest request) throws InvalidRequestException {
        ServiceResponse response = new ServiceResponse();

        book.saveBook(request);

        response.setStatusCode(HttpStatus.CREATED.value());
        response.setStatusMessage(HttpStatus.CREATED.getReasonPhrase());

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}
