package com.mariesto.book_reservation.persistence.repository;

import com.mariesto.book_reservation.persistence.entity_table.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepository extends JpaRepository<BookEntity, String> {

    @Transactional
    @Modifying
    @Query(value = "update BookEntity be set be.status = :status where be.ISBN = :bookId")
    void updateBookEntity(@Param(value = "bookId") String bookId, @Param(value = "status") String status);

}
