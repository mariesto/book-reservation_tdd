package com.mariesto.book_reservation.service;

import com.mariesto.book_reservation.common.InvalidRequestException;
import com.mariesto.book_reservation.common.NotFoundException;
import com.mariesto.book_reservation.persistence.gateway.BookGateway;
import com.mariesto.book_reservation.service.entity.BookListResponse;
import com.mariesto.book_reservation.service.entity.BookRequest;
import com.mariesto.book_reservation.service.entity.BookResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements Book {

    private BookGateway gateway;

    public BookService(BookGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public BookListResponse listBook() {
        BookListResponse response = new BookListResponse();
        List<BookResponse> books = gateway.findAll();

        if (books == null) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setStatusMessage(HttpStatus.OK.getReasonPhrase());
            return response;
        }

        response.setStatusCode(HttpStatus.OK.value());
        response.setStatusMessage(HttpStatus.OK.getReasonPhrase());
        response.setBooks(books);

        return response;
    }

    @Override
    public void saveBook(BookRequest request) throws InvalidRequestException {

        validateRequest(request);

        gateway.save(request);
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

        if (request.getStatus() == null) {
            throw new InvalidRequestException("Status can't be null!");
        }
    }

    @Override
    public BookResponse findBookById(String bookId) throws InvalidRequestException, NotFoundException {
        validateRequestId(bookId);

        return gateway.findBookById(bookId);
    }

    @Override
    public void deleteBook(String bookId) throws InvalidRequestException {
        validateRequestId(bookId);

        gateway.deleteBook(bookId);
    }

    @Override
    public void borrowBook(String bookId, String status) throws InvalidRequestException {
        validateRequestId(bookId);
        validateStatus(status);

        gateway.update(bookId, status);
    }

    private void validateStatus(String status) throws InvalidRequestException {
        if (status == null) {
            throw new InvalidRequestException("Status can't be null!");
        }
    }

    private void validateRequestId(String bookId) throws InvalidRequestException {
        if (bookId == null) {
            throw new InvalidRequestException("Id can't be null");
        }
    }
}
