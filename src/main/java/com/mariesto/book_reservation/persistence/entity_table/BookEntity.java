package com.mariesto.book_reservation.persistence.entity_table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookEntity {

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
