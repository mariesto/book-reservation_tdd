DROP TABLE IF EXISTS book;

CREATE TABLE book(
    isbn VARCHAR(20) PRIMARY KEY,
    title VARCHAR(250) NOT NULL,
    author VARCHAR(250) NOT NULL,
    published_date VARCHAR(10) NOT NULL,
    status VARCHAR(10) NOT NULL
);

INSERT INTO book (isbn, title, author, published_date, status)
VALUES ('ISBN-123', 'TDD with Spring Boot, JUnit, Mockito', 'Amendo', '24-02-2021', 'Available'),
('ISBN-234', 'Using Cache for Spring Boot', 'Amendo', '24-02-2022', 'Available');