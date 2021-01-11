package com.mariesto.book_reservation.service;

import com.mariesto.book_reservation.common.InvalidRequestException;
import com.mariesto.book_reservation.service.entity.BookRequest;
import com.mariesto.book_reservation.service.entity.BookResponse;

import java.util.List;

public interface Book {

    List<BookResponse> listBook();

    void saveBook(BookRequest request) throws InvalidRequestException;

    BookResponse findBookById(String bookId) throws InvalidRequestException;

    void deleteBook(String bookId) throws InvalidRequestException;

    void borrowBook(String bookId) throws InvalidRequestException;

}
