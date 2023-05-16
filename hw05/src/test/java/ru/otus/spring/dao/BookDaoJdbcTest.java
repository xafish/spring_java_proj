package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import(BookDaoJdbc.class)
public class BookDaoJdbcTest {
    private static final int EXPECTED_BOOKS_COUNT = 5;

    private static final Book EXPECTED_BOOK = new Book(
            5,
            "TestBookName_5",
            new Author(2, "TestNameAuthor_2", "TestLastnameAuthor_2"),
            new Genre(3,"TestGenre_3")
    );

    private static final int EXISTING_BOOK_ID = 1;
    @Autowired
    private BookDaoJdbc bookDao;

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        int actualPersonsCount = bookDao.count();
        assertThat(actualPersonsCount).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("возвращать книгу по ID")
    @Test
    void shouldReturnExpectedBook() {
        Book actualBook = bookDao.getById(EXPECTED_BOOK.getId());
        assertThat(actualBook).isEqualTo(EXPECTED_BOOK);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldCorrectDeleteBookById() {
        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        bookDao.deleteById(EXISTING_BOOK_ID);

        assertThatThrownBy(() -> bookDao.getById(EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book(
                6,
                "TestBookName_6",
                new Author(2, "TestNameAuthor_2", "TestLastnameAuthor_2"),
                new Genre(3,"TestGenre_3")
        );
        bookDao.insert(expectedBook);
        Book actualBook = bookDao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("изменять имя книги в БД")
    @Test
    void shouldUpdateBook() {
        String newName = "Test_updated_book";
        bookDao.updateNameById(EXPECTED_BOOK.getId(),newName);
        Book updatedBook = bookDao.getById(EXPECTED_BOOK.getId());
        assertThat(updatedBook.getName()).isEqualTo(newName);
    }

}
