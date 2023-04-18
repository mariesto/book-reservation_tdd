package com.mariesto.book_reservation.persistence.gateway;

import com.mariesto.book_reservation.common.NotFoundException;
import com.mariesto.book_reservation.persistence.entity.Book;
import com.mariesto.book_reservation.persistence.repository.BookRepository;
import com.mariesto.book_reservation.service.entity.BookRequest;
import com.mariesto.book_reservation.service.entity.BookResponse;

import java.util.ArrayList;
import java.util.List;

public class BookJPAGateway implements BookGateway {

    private final BookRepository repository;

    public BookJPAGateway(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BookResponse> findAll() {
        List<Book> bookEntities = repository.findAll();

        return constructBookResponses(bookEntities);
    }

    @Override
    public void save(BookRequest request) {
        Book entity = getBookEntity(request);

        repository.save(entity);
    }

    @Override
    public BookResponse findBookById(String bookId) throws NotFoundException {
        return repository.findById(bookId)
                .map(this::constructBookResponse)
                .orElseThrow(() -> new NotFoundException("Data Not Found with id : " + bookId));
    }

    @Override
    public void deleteBook(String bookId) {
        repository.deleteById(bookId);
    }

    @Override
    public void update(String bookId, String status) {
        repository.updateBookEntity(bookId, status);
    }

    private Book getBookEntity(BookRequest request) {
        Book entity = new Book();
        entity.setISBN(request.getISBN());
        entity.setTitle(request.getTitle());
        entity.setAuthor(request.getAuthor());
        entity.setPublishedDate(request.getPublishedDate());
        entity.setStatus(request.getStatus());
        return entity;
    }

    private BookResponse constructBookResponse(Book entity) {
        BookResponse response = new BookResponse();
        response.setISBN(entity.getISBN());
        response.setTitle(entity.getTitle());
        response.setAuthor(entity.getAuthor());
        response.setPublishedDate(entity.getPublishedDate());
        response.setStatus(entity.getStatus());

        return response;
    }

    private List<BookResponse> constructBookResponses(List<Book> book) {
        List<BookResponse> bookResponses = new ArrayList<>();

        for (Book entity : book) {
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

}
