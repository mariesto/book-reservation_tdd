package com.mariesto.book_reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariesto.book_reservation.persistence.entity_table.BookEntity;
import com.mariesto.book_reservation.persistence.repository.BookRepository;
import com.mariesto.book_reservation.service.entity.BookListResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository repository;

    private BookEntity entity;

    @BeforeEach
    void setUp() {
        entity = new BookEntity();
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
                .andExpect(jsonPath("$.books", hasSize(1)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();

        BookListResponse response = mapper.readValue(content, BookListResponse.class);

        assertEquals(200, result.getResponse().getStatus());
        assertEquals(1, response.getBooks().size());
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