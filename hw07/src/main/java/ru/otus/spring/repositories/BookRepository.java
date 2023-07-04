package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "genre", "comment"})
    List<Book> findAll();
    @EntityGraph(attributePaths = {"author", "genre", "comment"})
    Optional<Book> findById(Long id);
    @EntityGraph(attributePaths = {"author", "genre", "comment"})
    Book findByName(String name);

    /*List<Book> findAll();
    Book getById(long id);

    Book getByName(String name);

    void deleteById(long id);

    void updateNameById(Long id, String name);

    void addBook(Book book);*/
}
