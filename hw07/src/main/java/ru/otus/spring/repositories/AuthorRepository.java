package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    //Author getAuthorByNameLastname(String authorName, String authorLastName);

    Optional<Author> findByNameAndLastname(String authorName, String authorLastName);
}
