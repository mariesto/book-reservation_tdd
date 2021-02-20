package com.mariesto.book_reservation.persistence.gateway;

import com.mariesto.book_reservation.common.InvalidRequestException;
import com.mariesto.book_reservation.common.NotFoundException;
import com.mariesto.book_reservation.persistence.entity_table.BookEntity;
import com.mariesto.book_reservation.persistence.repository.BookRepository;
import com.mariesto.book_reservation.service.entity.BookRequest;
import com.mariesto.book_reservation.service.entity.BookResponse;

import java.util.ArrayList;
import java.util.List;

public class BookJPAGateway implements BookGateway {

    private BookRepository repository;

    public BookJPAGateway(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BookResponse> findAll() {
        List<BookEntity> bookEntities = repository.findAll();

        return constructBookResponses(bookEntities);
    }

    private List<BookResponse> constructBookResponses(List<BookEntity> bookEntity) {
        List<BookResponse> bookResponses = new ArrayList<>();

        for (BookEntity entity : bookEntity) {
            BookResponse bookResponse = new BookResponse();
            bookResponse.setISBN(entity.getISBN());
            bookResponse.setTitle(entity.getTitle());
            bookResponse.setAuthor(entity.getAuthor());
            bookResponse.setPublishedDate(entity.getPublishedDate());
            bookResponse.setStatus(entity.getStatus());

            bookResponses.add(bookResponse);
        }

        return bookResponses;
    }

    @Override
    public void save(BookRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("Request can't be null!");
        }

        BookEntity entity = getBookEntity(request);

        repository.save(entity);
    }

    private BookEntity getBookEntity(BookRequest request) {
        BookEntity entity = new BookEntity();
        entity.setISBN(request.getISBN());
        entity.setTitle(request.getTitle());
        entity.setAuthor(request.getAuthor());
        entity.setPublishedDate(request.getPublishedDate());
        entity.setStatus(request.getStatus());
        return entity;
    }

    @Override
    public BookResponse findBookById(String bookId) throws InvalidRequestException, NotFoundException {
        validateId(bookId);

        return repository.findById(bookId)
                .map(this::constructBookResponse)
                .orElseThrow(() -> new NotFoundException("Data Not Found with id : " + bookId));
    }

    private BookResponse constructBookResponse(BookEntity entity) {
        BookResponse response = new BookResponse();
        response.setISBN(entity.getISBN());
        response.setTitle(entity.getTitle());
        response.setAuthor(entity.getAuthor());
        response.setPublishedDate(entity.getPublishedDate());
        response.setStatus(entity.getStatus());

        return response;
    }

    @Override
    public void deleteBook(String bookId) throws InvalidRequestException {
        validateId(bookId);

        repository.deleteById(bookId);
    }

    @Override
    public void update(String bookId, String status) throws InvalidRequestException {
        validateId(bookId);

        repository.updateBookEntity(bookId, status);
    }

    private void validateId(String bookId) throws InvalidRequestException {
        if (bookId == null) {
            throw new InvalidRequestException("Id can't be null!");
        }
    }
}
