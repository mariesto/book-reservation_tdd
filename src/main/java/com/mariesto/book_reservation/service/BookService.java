package com.mariesto.book_reservation.service;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.stereotype.Service;
import com.mariesto.book_reservation.common.InvalidRequestException;
import com.mariesto.book_reservation.common.NotFoundException;
import com.mariesto.book_reservation.persistence.gateway.BookGateway;
import com.mariesto.book_reservation.service.cache.CacheProvider;
import com.mariesto.book_reservation.service.entity.BookRequest;
import com.mariesto.book_reservation.service.entity.BookResponse;

@Service
public class BookService implements Book {
    private final BookGateway gateway;

    private final CacheProvider cacheProvider;

    @Value ("${cache-name}")
    private String cacheName;

    public BookService(BookGateway gateway, CacheProvider cacheProvider) {
        this.gateway = gateway;
        this.cacheProvider = cacheProvider;
    }

    @Override
    public List<BookResponse> listBook() {
        return gateway.findAll();
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
        try {
            long time = 2000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }

        BookResponse response = gateway.findBookById(bookId);

        Object cache = cacheProvider.get(cacheName, bookId);
        if (Objects.isNull(cache)) {
            cacheProvider.put(cacheName, bookId, response);
        } else {
            Object cacheResult = cacheProvider.get(cacheName, bookId);
            SimpleValueWrapper simpleValueWrapper = (SimpleValueWrapper) cacheResult;
            response= (BookResponse) simpleValueWrapper.get();
        }

        return response;
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
