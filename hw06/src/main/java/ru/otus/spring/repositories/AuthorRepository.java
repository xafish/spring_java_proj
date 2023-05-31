package ru.otus.spring.repositories;

import ru.otus.spring.domain.Author;

public interface AuthorRepository {
    Author getAuthorByNameLastname(String authorName, String authorLastName);
}
