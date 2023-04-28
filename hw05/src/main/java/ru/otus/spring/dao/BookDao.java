package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

public interface BookDao {

    int count();

    void insert(Book person);

    Book getById(long id);

    List<Book> getAll();

    void deleteById(long id);

    void updateNameById(long id, String name);

    Author getAuthorByNameLastname(String name, String lastName);

    Genre getGenreByName(String name);
}
