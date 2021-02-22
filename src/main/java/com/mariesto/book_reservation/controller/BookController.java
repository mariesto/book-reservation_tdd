package com.mariesto.book_reservation.controller;

import com.mariesto.book_reservation.common.InvalidRequestException;
import com.mariesto.book_reservation.common.NotFoundException;
import com.mariesto.book_reservation.service.Book;
import com.mariesto.book_reservation.service.entity.BookListResponse;
import com.mariesto.book_reservation.service.entity.BookRequest;
import com.mariesto.book_reservation.service.entity.BookResponse;
import com.mariesto.book_reservation.service.entity.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final Book book;

    public BookController(Book book) {
        this.book = book;
    }

    @GetMapping(path = "/")
    public ResponseEntity<Object> listBook(){
        List<BookResponse> listBook = book.listBook();

        BookListResponse response = new BookListResponse();
        response.setBooks(listBook);
        response.setStatusCode(HttpStatus.OK.value());
        response.setStatusMessage(HttpStatus.OK.getReasonPhrase());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createBook(@RequestBody BookRequest request) throws InvalidRequestException {
        ServiceResponse response = new ServiceResponse();

        book.saveBook(request);

        response.setStatusCode(HttpStatus.CREATED.value());
        response.setStatusMessage(HttpStatus.CREATED.getReasonPhrase());

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping(path = "/{isbn}")
    public ResponseEntity<Object> findBookById(@PathVariable String isbn) throws InvalidRequestException, NotFoundException {
        BookResponse response = book.findBookById(isbn);

        response.setStatusCode(HttpStatus.OK.value());
        response.setStatusMessage(HttpStatus.OK.getReasonPhrase());

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Object> deleteBook(@PathVariable String isbn) throws InvalidRequestException, NotFoundException {
        ServiceResponse response = new ServiceResponse();

        book.findBookById(isbn);

        book.deleteBook(isbn);

        response.setStatusCode(HttpStatus.NO_CONTENT.value());
        response.setStatusMessage(HttpStatus.NO_CONTENT.getReasonPhrase());

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
