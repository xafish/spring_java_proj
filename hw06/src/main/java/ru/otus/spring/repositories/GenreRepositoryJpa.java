package ru.otus.spring.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;
@AllArgsConstructor
@Repository
public class GenreRepositoryJpa implements GenreRepository{

    @PersistenceContext
    private final EntityManager em;
    @Override
    public Genre getGenreByName(String name) {
        var query = em.createQuery("select g from Genre g where g.name = :name", Genre.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
}
