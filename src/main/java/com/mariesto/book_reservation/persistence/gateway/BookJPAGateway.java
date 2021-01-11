package com.mariesto.book_reservation.persistence.gateway;

import com.mariesto.book_reservation.persistence.entity_table.BookEntity;
import com.mariesto.book_reservation.persistence.repository.BookRepository;
import com.mariesto.book_reservation.service.entity.BookResponse;

import java.util.List;

public class BookJPAGateway implements BookGateway {

    private BookRepository repository;

    public BookJPAGateway(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BookResponse> findAll() {
        return null;
    }

    @Override
    public void save(BookEntity entity) {
        repository.save(entity);
    }

    @Override
    public com.mariesto.book_reservation.service.entity.BookResponse findBookById(String bookId) {
//        return repository.findById(bookId);
        return null;
    }

    @Override
    public void deleteBook(String bookId) {
        repository.deleteById(bookId);
    }

    @Override
    public void update(String bookId) {

    }
}
