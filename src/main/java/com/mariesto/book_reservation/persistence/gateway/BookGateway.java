package com.mariesto.book_reservation.persistence.gateway;

import com.mariesto.book_reservation.persistence.entity_table.BookEntity;
import com.mariesto.book_reservation.service.entity.BookResponse;

import java.util.List;

public interface BookGateway {

    List<BookResponse> findAll();

    void save(BookEntity entity);

    BookResponse findBookById(String bookId);

    void deleteBook(String bookId);

    void update(String bookId);

}
