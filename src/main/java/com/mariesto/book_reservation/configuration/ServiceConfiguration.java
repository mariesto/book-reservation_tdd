package com.mariesto.book_reservation.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.mariesto.book_reservation.persistence.gateway.BookGateway;
import com.mariesto.book_reservation.persistence.gateway.BookJPAGateway;
import com.mariesto.book_reservation.persistence.repository.BookRepository;
import com.mariesto.book_reservation.service.Book;
import com.mariesto.book_reservation.service.BookService;
import com.mariesto.book_reservation.service.cache.CacheProvider;
import com.mariesto.book_reservation.service.cache.CacheProviderFactory;

@Configuration
public class ServiceConfiguration {
    @Autowired
    private CacheProviderFactory cacheProviderFactory;

    @Bean
    public BookGateway gateway(BookRepository repository) {
        return new BookJPAGateway(repository);
    }

    @Bean
    public CacheProvider cacheProvider() {
        return cacheProviderFactory.getCacheProvider();
    }

    @Bean
    public Book book(BookGateway gateway) {
        return new BookService(gateway, cacheProvider());
    }

}
