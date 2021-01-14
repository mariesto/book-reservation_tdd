package com.mariesto.book_reservation.persistence.gateway;

import com.mariesto.book_reservation.common.InvalidRequestException;
import com.mariesto.book_reservation.common.NotFoundException;
import com.mariesto.book_reservation.service.entity.BookRequest;
import com.mariesto.book_reservation.service.entity.BookResponse;

import java.util.List;

public interface BookGateway {

    List<BookResponse> findAll();

    void save(BookRequest request) throws InvalidRequestException;

    BookResponse findBookById(String bookId) throws InvalidRequestException, NotFoundException;

    void deleteBook(String bookId) throws InvalidRequestException;

    void update(String bookId, String status) throws InvalidRequestException;

}
