package com.mariesto.book_reservation.persistence.gateway;

import com.mariesto.book_reservation.common.NotFoundException;
import com.mariesto.book_reservation.service.entity.BookRequest;
import com.mariesto.book_reservation.service.entity.BookResponse;

import java.util.List;

public interface BookGateway {

    void save(BookRequest request);

    List<BookResponse> findAll();

    BookResponse findBookById(String bookId) throws NotFoundException;

    void update(String bookId, String status);

    void deleteBook(String bookId);

}
