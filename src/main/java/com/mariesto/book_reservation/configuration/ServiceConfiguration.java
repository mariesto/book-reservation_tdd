package com.mariesto.book_reservation.configuration;

import com.mariesto.book_reservation.persistence.gateway.BookGateway;
import com.mariesto.book_reservation.persistence.gateway.BookJPAGateway;
import com.mariesto.book_reservation.persistence.repository.BookRepository;
import com.mariesto.book_reservation.service.Book;
import com.mariesto.book_reservation.service.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public BookGateway gateway(BookRepository repository){
        return new BookJPAGateway(repository);
    }

    @Bean
    public Book book(BookGateway gateway){
        return new BookService(gateway);
    }
}
