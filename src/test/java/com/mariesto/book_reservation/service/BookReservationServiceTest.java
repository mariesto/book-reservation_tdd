package com.mariesto.book_reservation.service;

import com.mariesto.book_reservation.common.InvalidRequestException;
import com.mariesto.book_reservation.common.NotFoundException;
import com.mariesto.book_reservation.persistence.gateway.BookGateway;
import com.mariesto.book_reservation.service.entity.BookRequest;
import com.mariesto.book_reservation.service.entity.BookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookReservationServiceTest {

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

    private final List<BookResponse> predefinedListBook = Arrays.asList(new BookResponse(), new BookResponse());

    @Test
    void givenARequest_whenFindAllBookButNoDataFound_shouldReturnEmptyCollection() {
        when(gateway.findAll()).thenReturn(new ArrayList<>());

        List<BookResponse> response = useCase.listBook();

        assertTrue(response.isEmpty());
    }

    @Test
    void givenARequest_whenGetAllBook_shouldReturnBooks() {
        when(gateway.findAll()).thenReturn(predefinedListBook);

        List<BookResponse> response = useCase.listBook();

        assertEquals(2, response.size());
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
    void givenNullStatus_whenSaveBook_shouldThrowException() {
        request.setISBN("ISBN-1234");
        request.setTitle("Learn TDD");
        request.setAuthor("Amendo");
        request.setPublishedDate("12-01-2021");
        request.setStatus(null);

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
    void givenIdButNoData_whenGetBook_shouldThrowException() throws InvalidRequestException, NotFoundException {
        when(gateway.findBookById(anyString())).thenReturn(null);

        BookResponse book = useCase.findBookById("ISBN-1234");
        assertNull(book);
    }

    @Test
    void givenId_whenGetBook_shouldReturnBook() throws InvalidRequestException, NotFoundException {
        when(gateway.findBookById(anyString())).thenReturn(getAvailableBookResponse());

        BookResponse book = useCase.findBookById("ISBN-123");

        assertEquals("ISBN-123", book.getISBN());
        assertEquals("Learn TDD", book.getTitle());
        assertEquals("Amendo", book.getAuthor());
        assertEquals("12-01-2020", book.getPublishedDate());
        assertEquals("Available", book.getStatus());

        verify(gateway, times(1)).findBookById(anyString());
    }

    @Test
    void givenNullId_whenDeleteBook_shouldThrowException() {
        assertThrows(InvalidRequestException.class, () -> useCase.deleteBook(null));
    }

    @Test
    void givenId_whenDeleteBook_shouldDoCorrectFunction() throws InvalidRequestException, NotFoundException {
        when(gateway.findBookById(anyString())).thenReturn(getAvailableBookResponse());

        useCase.deleteBook("ISBN-123");

        verify(gateway, times(1)).deleteBook(anyString());
    }

    @Test
    void givenNullId_whenBorrowBook_shouldThrowException() {
        assertThrows(InvalidRequestException.class, () -> useCase.borrowBook(null, null));
    }

    @Test
    void givenIdAndNullStatusRequest_whenBorrowBook_shouldThrowException() {
        assertThrows(InvalidRequestException.class, () -> useCase.borrowBook("ISBN-1234", null));
    }

    @Test
    void givenId_whenBorrowBook_shouldDoCorrectFunction() throws InvalidRequestException, NotFoundException {
        when(gateway.findBookById(anyString())).thenReturn(getBookedBookResponse());

        useCase.borrowBook("ISBN-123", "Booked");

        verify(gateway, times(1)).update(anyString(), anyString());

        BookResponse book = useCase.findBookById("ISBN-123");

        assertNotNull(book);
        assertEquals("ISBN-123", book.getISBN());
        assertEquals("Learn TDD", book.getTitle());
        assertEquals("Amendo", book.getAuthor());
        assertEquals("12-01-2020", book.getPublishedDate());
        assertEquals("Booked", book.getStatus());
    }

    private BookRequest getBookRequest(){
        BookRequest request = new BookRequest();
        request.setISBN("ISBN-123");
        request.setTitle("Learn TDD");
        request.setAuthor("Amendo");
        request.setPublishedDate("12-01-2020");
        request.setStatus("Available");

        return request;
    }

    private BookResponse getAvailableBookResponse(){
        BookResponse response = new BookResponse();
        response.setISBN("ISBN-123");
        response.setTitle("Learn TDD");
        response.setAuthor("Amendo");
        response.setPublishedDate("12-01-2020");
        response.setStatus("Available");

        return response;
    }

    private BookResponse getBookedBookResponse(){
        BookResponse response = new BookResponse();
        response.setISBN("ISBN-123");
        response.setTitle("Learn TDD");
        response.setAuthor("Amendo");
        response.setPublishedDate("12-01-2020");
        response.setStatus("Booked");

        return response;
    }
}
