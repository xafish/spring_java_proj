package ru.otus.spring.repositories;

import ru.otus.spring.domain.Genre;

public interface GenreRepository {
    Genre getGenreByName(String name);
}
