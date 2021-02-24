package com.mariesto.book_reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariesto.book_reservation.persistence.entity_table.Book;
import com.mariesto.book_reservation.persistence.repository.BookRepository;
import com.mariesto.book_reservation.service.entity.BookListResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class BookControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private BookController bookController;

    @Autowired
    private BookRepository repository;

    private Book entity;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setControllerAdvice(ExceptionController.class)
                .build();

        entity = new Book();
    }

    @AfterEach
    void tearDown() {
        if (entity != null) {
            repository.delete(entity);
        }
    }

    @Test
    void givenARequest_whenGetAllBookButEmptyReturn_shouldReturnEmptyCollection() throws Exception {
        MvcResult result = mockMvc.perform(get("/books/").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", equalTo(200)))
                .andExpect(jsonPath("$.statusMessage", equalTo(HttpStatus.OK.getReasonPhrase())))
                .andExpect(jsonPath("$.books", hasSize(0)))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void givenARequest_whenGetAllBook_shouldReturnCorrectResponse() throws Exception {
        preparedData();

        MvcResult result = mockMvc.perform(get("/books/").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", equalTo(200)))
                .andExpect(jsonPath("$.statusMessage", equalTo(HttpStatus.OK.getReasonPhrase())))
                .andExpect(jsonPath("$.books", hasSize(1)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();

        BookListResponse response = mapper.readValue(content, BookListResponse.class);

        assertEquals(200, result.getResponse().getStatus());
        assertEquals(1, response.getBooks().size());
    }

    @Test
    void givenNullRequest_whenSaveBook_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode", equalTo(HttpStatus.BAD_REQUEST.value())));
    }

    @Test
    void givenInvalidRequest_whenSaveBook_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"ISBN\": \"ISBN-1234\",\n" +
                        "  \"title\": \"Learn TDD\",\n" +
                        "  \"author\": \"\",\n" +
                        "  \"publishedDate\": \"20 Feb 2020\",\n" +
                        "  \"status\": \"Available\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode", equalTo(HttpStatus.BAD_REQUEST.value())));
    }

    @Test
    void givenARequest_whenSaveBook_shouldReturnCorrectResponse() throws Exception {
        mockMvc.perform(post("/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"isbn\": \"ISBN-1234\",\n" +
                        "  \"title\": \"Learn TDD\",\n" +
                        "  \"author\": \"Amendo\",\n" +
                        "  \"publishedDate\": \"20 Feb 2020\",\n" +
                        "  \"status\": \"Available\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode", equalTo(HttpStatus.CREATED.value())));

        Optional<Book> bookEntity = repository.findById("ISBN-1234");

        assertTrue(bookEntity.isPresent());
    }

    @Test
    void givenId_whenFindBookByIdButNotFoundData_shouldReturnNotFoundMessage() throws Exception {
        preparedData();

        String isbn = "ISBN-0000";

        mockMvc.perform(get("/books/{isbn}", isbn))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode", equalTo(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    void givenId_whenFindBookById_shouldReturnCorrectData() throws Exception {
        preparedData();

        String isbn = "ISBN-123";

        mockMvc.perform(get("/books/{isbn}", isbn))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", equalTo(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.isbn", equalTo(isbn)));
    }

    @Test
    void givenId_whenDeleteBook_shouldDeleteCorrectData() throws Exception {
        preparedData();

        String isbn = "ISBN-123";

        mockMvc.perform(delete("/books/{isbn}", isbn))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.statusCode", equalTo(HttpStatus.NO_CONTENT.value())));

        Optional<Book> bookEntity = repository.findById(isbn);
        assertFalse(bookEntity.isPresent());
    }

    @Test
    void givenId_whenDeleteBookButBookNotFound_shouldReturnNotFound() throws Exception {
        preparedData();

        String isbn = "ISBN-12345";

        mockMvc.perform(delete("/books/{isbn}", isbn))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode", equalTo(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    void givenARequest_whenBorrowBookButBookNotFound_shouldReturnNotFound() throws Exception {
        preparedData();

        String isbn = "ISBN-1234";
        String status = "Available";

        mockMvc.perform(patch("/books/{isbn}/{status}", isbn, status))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode", equalTo(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    void givenARequest_whenBorrowBook_shouldChangeBookStatus() throws Exception {
        preparedData();

        String isbn = "ISBN-123";
        String status = "Available";

        mockMvc.perform(patch("/books/{isbn}/{status}", isbn, status))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.statusCode", equalTo(HttpStatus.NO_CONTENT.value())));

        Optional<Book> entity = repository.findById(isbn);

        assertTrue(entity.isPresent());
        assertEquals(status, entity.get().getStatus());
    }

    private void preparedData() {
        entity.setISBN("ISBN-123");
        entity.setTitle("Learn TDD");
        entity.setAuthor("Amendo");
        entity.setPublishedDate("12-01-2020");
        entity.setStatus("Booked");
        repository.save(entity);
    }
}