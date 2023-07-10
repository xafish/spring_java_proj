package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author"})
    List<Book> findAll();

    @EntityGraph(attributePaths = {"author"})
    Optional<Book> findById(Long id);
    Optional<Book> findByName(String name);

}
