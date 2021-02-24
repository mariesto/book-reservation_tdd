package com.mariesto.book_reservation.persistence.entity_table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    private String ISBN;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String publishedDate;

    @Column
    private String status;

}
