package ru.otus.spring.dao;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.service.BookLibraryService;
import ru.otus.spring.service.BookLibraryServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Сервис для работы с книгами должен")
@DataJpaTest
@Import(BookLibraryServiceImpl.class)
public class BookLibraryServiceTest {

    @PersistenceContext
    private EntityManager em;

    private static final int EXPECTED_BOOKS_COUNT = 5;

    private static final Book EXPECTED_BOOK = new Book(
            5L,
            "TestBookName_5",
            new Author(2L, "TestNameAuthor_2", "TestLastnameAuthor_2"),
            new Genre(3L,"TestGenre_3")
    );

    private static final Long EXISTING_BOOK_ID = 2L;

    private static final Long EXISTING_COMMENT_ID = 2L;


    @Autowired
    private BookLibraryService bookLibraryService;

    @DisplayName("возвращать все книги в БД")
    @Test
    void shouldReturnAllBooks() {
        List<Book> allBook = bookLibraryService.getAllBook();
        assertThat(allBook).isNotEmpty();
    }

    @DisplayName("возвращать книгу по ID")
    @Test
    void shouldReturnExpectedBook() {
        Book actualBook = bookLibraryService.getBookById(EXPECTED_BOOK.getId());
        assertThat(actualBook.equals(EXPECTED_BOOK)).isTrue();
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldCorrectDeleteBookById() {
        assertThat(bookLibraryService.getBookById(EXISTING_BOOK_ID)).isNotNull();

        bookLibraryService.deleteBook(EXISTING_BOOK_ID);

        assertThat(bookLibraryService.getBookById(EXISTING_BOOK_ID)).isEqualTo(null);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book(
                6L,
                "TestBookName_6",
                new Author(2L, "TestNameAuthor_2", "TestLastnameAuthor_2"),
                new Genre(3L,"TestGenre_3")
        );

        bookLibraryService.addBook(expectedBook.getId(), expectedBook.getName(), expectedBook.getAuthor().getName(), expectedBook.getAuthor().getLastname(), expectedBook.getGenre().getName());
        Book actualBook = bookLibraryService.getBookById(expectedBook.getId());
        assertThat(actualBook.equals(expectedBook)).isTrue();
    }

    @DisplayName("изменять имя книги в БД")
    @Test
    void shouldUpdateBook() {
        String newName = "Test_updated_book";
        bookLibraryService.setName(EXPECTED_BOOK.getId(),newName);
        Book updatedBook = bookLibraryService.getBookById(EXPECTED_BOOK.getId());
        assertThat(updatedBook.getName().equals(newName)).isTrue();
    }

    @DisplayName("возвращать все комментарии")
    @Test
    void shouldReturnExpectedBookCount() {
        String allComment = bookLibraryService.getAllComment();
        assertThat(allComment).isNotEmpty();
    }

    @DisplayName("возвращать комментарии по имени книги и ID")
    @Test
    void shouldReturnExpectedComment() {
        String comments = bookLibraryService.getCommentsByBookName("TestBookName_1");
        assertThat(comments).isNotEmpty();
    }

    @DisplayName("добавлять комментарий в БД")
    @Test
    void shouldInsertComment() {



        Comment expectedComment = new Comment(
                em.find(Book.class,1L),
                "TestComment5",
                "TestUser5"
        );
        // проверяем что ещё нет
        String actualComment = bookLibraryService.getAllComment();
        assertThat(actualComment.contains(expectedComment.getText())).isFalse();

        bookLibraryService.addComment(expectedComment.getBook().getId(), expectedComment.getText(), expectedComment.getUserName());

        actualComment = bookLibraryService.getCommentsByBookName(expectedComment.getBook().getName());

        assertThat(actualComment.contains(expectedComment.getBook().toString())).isTrue();
        assertThat(actualComment.contains(expectedComment.getText())).isTrue();
        assertThat(actualComment.contains(expectedComment.getUserName())).isTrue();
    }

    @DisplayName("удалить комментарий в БД")
    @Test
    void shouldDeleteComment() {
        assertThat(bookLibraryService.getAllComment().contains("Comment{id="+EXISTING_COMMENT_ID)).isTrue();

        bookLibraryService.deleteComment(EXISTING_COMMENT_ID);

        assertThat(bookLibraryService.getAllComment().contains("Comment{id="+EXISTING_COMMENT_ID)).isFalse();
    }

}
