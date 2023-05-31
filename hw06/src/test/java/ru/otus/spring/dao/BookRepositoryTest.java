package ru.otus.spring.dao;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.BookRepositoryJpa;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с книгами должно")
@DataJpaTest
@Import(BookRepositoryJpa.class)
public class BookRepositoryTest {
    private static final int EXPECTED_BOOKS_COUNT = 5;

    private static final Book EXPECTED_BOOK = new Book(
            5L,
            "TestBookName_5",
            new Author(2L, "TestNameAuthor_2", "TestLastnameAuthor_2"),
            new Genre(3L,"TestGenre_3")
    );

    private static final int EXISTING_BOOK_ID = 2;
    @Autowired
    private BookRepository bookRepository;

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        Long actualPersonsCount = bookRepository.findAll().stream().count();
        assertThat(actualPersonsCount).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("возвращать книгу по ID")
    @Test
    void shouldReturnExpectedBook() {
        Book actualBook = bookRepository.getById(EXPECTED_BOOK.getId());
        assertThat(actualBook).isEqualTo(EXPECTED_BOOK);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldCorrectDeleteBookById() {
        assertThatCode(() -> bookRepository.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        bookRepository.deleteById(EXISTING_BOOK_ID);

        assertThatThrownBy(() -> bookRepository.getById(EXISTING_BOOK_ID))
                .isInstanceOf(NoResultException.class);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book(
                6L,
                "TestBookName_6",
                new Author(2L, "TestNameAuthor_2", "TestLastnameAuthor_2"),
                new Genre(3,"TestGenre_3")
        );
        bookRepository.addBook(expectedBook);
        Book actualBook = bookRepository.getById(expectedBook.getId());
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("изменять имя книги в БД")
    @Test
    void shouldUpdateBook() {
        String newName = "Test_updated_book";
        bookRepository.updateNameById(EXPECTED_BOOK.getId(),newName);
        Book updatedBook = bookRepository.getById(EXPECTED_BOOK.getId());
        assertThat(updatedBook.getName()).isEqualTo(newName);
    }

}
