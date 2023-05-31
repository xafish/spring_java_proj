package ru.otus.spring.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

@AllArgsConstructor
@Repository
public class AuthorRepositoryJpa implements  AuthorRepository{

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Author getAuthorByNameLastname(String authorName, String authorLastName) {
        var query = em.createQuery("select a from Author a where a.name = :name and a.lastname = :lastname", Author.class);
        query.setParameter("name", authorName);
        query.setParameter("lastname", authorLastName);
        return query.getSingleResult();
    }
}
