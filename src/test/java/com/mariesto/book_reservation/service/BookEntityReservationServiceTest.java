package com.mariesto.book_reservation.service;

import com.mariesto.book_reservation.common.InvalidRequestException;
import com.mariesto.book_reservation.persistence.gateway.BookGateway;
import com.mariesto.book_reservation.service.entity.BookRequest;
import com.mariesto.book_reservation.service.entity.BookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookEntityReservationServiceTest {

    private Book useCase;

    @Mock
    private BookGateway gateway;

    private BookRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        useCase = new BookService(gateway);
        request = new BookRequest();
    }

    private List<BookResponse> predefinedListBook = Arrays.asList(new BookResponse(), new BookResponse());

    @Test
    void whenFindAllBookButNoDataFound_shouldReturnEmptyCollection() {
        when(gateway.findAll()).thenReturn(null);

        List<BookResponse> listBook = useCase.listBook();

        assertTrue(listBook.isEmpty());
    }

    @Test
    void whenGetAllBook_shouldReturnBooks() {
        when(gateway.findAll()).thenReturn(predefinedListBook);

        List<BookResponse> listBook = useCase.listBook();

        assertEquals(2, listBook.size());
        verify(gateway, times(1)).findAll();
    }

    @Test
    void givenNullRequest_whenSaveBook_shouldThrowException() {
        assertThrows(InvalidRequestException.class, () -> useCase.saveBook(null));
    }

    @Test
    void givenEmptyRequest_whenSaveBook_shouldThrowException() {
        assertThrows(InvalidRequestException.class, () -> useCase.saveBook(request));
    }

    @Test
    void givenNullTitleRequest_whenSaveBook_shouldThrowException() {
        request.setISBN("ISBN-1234");
        request.setTitle(null);
        request.setAuthor("Amendo");
        request.setPublishedDate("12-01-2021");

        assertThrows(InvalidRequestException.class, () -> useCase.saveBook(request));
    }

    @Test
    void givenNullAuthorRequest_whenSaveBook_shouldThrowException() {
        request.setISBN("ISBN-1234");
        request.setTitle("Learn TDD");
        request.setAuthor(null);
        request.setPublishedDate("12-01-2021");

        assertThrows(InvalidRequestException.class, () -> useCase.saveBook(request));
    }

    @Test
    void givenNullPublishedDateRequest_whenSaveBook_shouldThrowException() {
        request.setISBN("ISBN-1234");
        request.setTitle("Learn TDD");
        request.setAuthor("Amendo");

        assertThrows(InvalidRequestException.class, () -> useCase.saveBook(request));
    }

    @Test
    void givenValidRequest_whenSaveBook_shouldDoCorrectFunction() throws InvalidRequestException {
        useCase.saveBook(getBookRequest());

        verify(gateway, times(1)).save(any());
    }

    @Test
    void givenNullId_whenGetBook_shouldThrowException() {
        assertThrows(InvalidRequestException.class, () -> useCase.findBookById(null));
    }

    @Test
    void givenIdButNoData_whenGetBook_shouldThrowException() throws InvalidRequestException {
        when(gateway.findBookById(anyString())).thenReturn(null);

        BookResponse book = useCase.findBookById("ISBN-1234");
        assertNull(book);
    }

    @Test
    void givenId_whenGetBook_shouldReturnBook() throws InvalidRequestException {
        when(gateway.findBookById(anyString())).thenReturn(getBookResponse());

        BookResponse book = useCase.findBookById("ISBN-1234");

        assertEquals(book, getBookResponse());

        verify(gateway, times(1)).findBookById(anyString());
    }

    @Test
    void givenNullId_whenDeleteBook_shouldThrowException() {
        assertThrows(InvalidRequestException.class, () -> useCase.deleteBook(null));
    }

    @Test
    void givenId_whenDeleteBook_shouldDoCorrectFunction() throws InvalidRequestException {
        useCase.deleteBook("ISBN-1234");

        verify(gateway, times(1)).deleteBook(anyString());
    }

    @Test
    void givenNullId_whenBorrowBook_shouldThrowException() {
        assertThrows(InvalidRequestException.class, () -> useCase.borrowBook(null));
    }

    @Test
    void givenId_whenBorrowBook_shouldDoCorrectFunction() throws InvalidRequestException {
        when(gateway.findBookById(anyString())).thenReturn(getBookResponse());

        useCase.borrowBook("ISBN-123");

        verify(gateway, times(1)).update(anyString());

        BookResponse book = useCase.findBookById("ISBN-123");
        assertNotNull(book);
        assertEquals("Available", book.getStatus());
    }

    private BookRequest getBookRequest(){
        BookRequest request = new BookRequest();
        request.setISBN("ISBN-123");
        request.setTitle("Learn TDD");
        request.setAuthor("Amendo");
        request.setPublishedDate("12-01-2020");

        return request;
    }

    private BookResponse getBookResponse(){
        BookResponse response = new BookResponse();
        response.setISBN("ISBN-123");
        response.setTitle("Learn TDD");
        response.setAuthor("Amendo");
        response.setPublishedDate("12-01-2020");
        response.setStatus("Available");

        return response;
    }
}
