package com.mariesto.book_reservation.persistence;

import com.mariesto.book_reservation.common.InvalidRequestException;
import com.mariesto.book_reservation.common.NotFoundException;
import com.mariesto.book_reservation.persistence.entity.Book;
import com.mariesto.book_reservation.persistence.gateway.BookJPAGateway;
import com.mariesto.book_reservation.persistence.repository.BookRepository;
import com.mariesto.book_reservation.service.entity.BookRequest;
import com.mariesto.book_reservation.service.entity.BookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookJPAGatewayTest {

    @Mock
    private BookRepository repository;

    private BookJPAGateway jpaGateway;

    private BookRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        jpaGateway = new BookJPAGateway(repository);
        request = new BookRequest();
    }

    @Test
    void givenValidData_whenSaveBook_shouldDoCorrectFunctionOnce() throws InvalidRequestException {
        Book entity = getBookEntity();

        when(repository.save(any())).thenReturn(entity);

        jpaGateway.save(request);

        verify(repository, times(1)).save(any());
    }

    @Test
    void givenARequest_whenFindAllBook_shouldReturnAllBook() {
        when(repository.findAll()).thenReturn(entityList);

        List<BookResponse> bookResponses = jpaGateway.findAll();

        assertNotNull(bookResponses);
        assertEquals(2, bookResponses.size());

        verify(repository, times(1)).findAll();
    }

    @Test
    void givenId_whenFindById_shouldReturnCorrectData() throws NotFoundException {
        when(repository.findById(anyString())).thenReturn(Optional.of(getBookEntity()));

        BookResponse book = jpaGateway.findBookById("ISBN-1234");

        assertNotNull(book);
        assertEquals("ISBN-1234", book.getISBN());
        assertEquals("Learn TDD", book.getTitle());
        assertEquals("Amendo", book.getAuthor());
        assertEquals("12-01-2020", book.getPublishedDate());
        assertEquals("Available", book.getStatus());

        verify(repository, times(1)).findById(anyString());
    }

    @Test
    void givenId_whenDeleteBook_shouldDoCorrectFunction() {
        jpaGateway.deleteBook("ISBN-1234");

        verify(repository, times(1)).deleteById(anyString());
    }

    @Test
    void givenValidRequest_whenUpdateBook_shouldDoUpdateOnce() {
        jpaGateway.update("ISBN-1234", "Booked");

        verify(repository, times(1)).updateBookEntity(anyString(), anyString());
    }

    private final List<Book> entityList = Arrays.asList(new Book(), new Book());

    private Book getBookEntity() {
        Book entity = new Book();
        entity.setISBN("ISBN-1234");
        entity.setTitle("Learn TDD");
        entity.setAuthor("Amendo");
        entity.setPublishedDate("12-01-2020");
        entity.setStatus("Available");
        return entity;
    }
}