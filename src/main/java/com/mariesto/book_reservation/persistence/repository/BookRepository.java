package com.mariesto.book_reservation.persistence.repository;

import com.mariesto.book_reservation.persistence.entity_table.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, String> {
}
