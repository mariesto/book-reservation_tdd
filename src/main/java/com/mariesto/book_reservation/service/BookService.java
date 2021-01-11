package com.mariesto.book_reservation.service;

import com.mariesto.book_reservation.common.InvalidRequestException;
import com.mariesto.book_reservation.persistence.entity_table.BookEntity;
import com.mariesto.book_reservation.persistence.gateway.BookGateway;
import com.mariesto.book_reservation.service.entity.BookRequest;
import com.mariesto.book_reservation.service.entity.BookResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BookService implements Book {

    private BookGateway gateway;

    public BookService(BookGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<BookResponse> listBook() {

        List<BookResponse> books = gateway.findAll();

        if (books == null) {
            return Collections.emptyList();
        }

        return books;
    }

    @Override
    public void saveBook(BookRequest request) throws InvalidRequestException {

        validateRequest(request);

        BookEntity entity = new BookEntity();
        entity.setISBN(request.getISBN());
        entity.setTitle(request.getTitle());
        entity.setAuthor(request.getAuthor());
        entity.setPublishedDate(request.getPublishedDate());
        entity.setStatus(request.getStatus());

        gateway.save(entity);
    }

    private void validateRequest(BookRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("Request can't be null!");
        }

        if (request.getISBN() == null) {
            throw new InvalidRequestException("ISBN can't be null!");
        }

        if (request.getTitle() == null) {
            throw new InvalidRequestException("Title can't be null!");
        }

        if (request.getAuthor() == null) {
            throw new InvalidRequestException("Author can't be null!");
        }

        if (request.getPublishedDate() == null) {
            throw new InvalidRequestException("Published Date can't be null!");
        }
    }

    @Override
    public BookResponse findBookById(String bookId) throws InvalidRequestException {
        validateRequestId(bookId);

        return gateway.findBookById(bookId);
    }

    @Override
    public void deleteBook(String bookId) throws InvalidRequestException {
        validateRequestId(bookId);

        gateway.deleteBook(bookId);
    }

    @Override
    public void borrowBook(String bookId) throws InvalidRequestException {
        validateRequestId(bookId);

        gateway.update(bookId);
    }

    private void validateRequestId(String bookId) throws InvalidRequestException {
        if (bookId == null) {
            throw new InvalidRequestException("Id can't be null");
        }
    }
}
