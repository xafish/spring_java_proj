package ru.otus.spring.repositories;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookRepository {
    List<Book> findAll();
    Book getById(long id);

    Book getByName(String name);

    void deleteById(long id);

    void updateNameById(Long id, String name);

    void addBook(Book book);
}
