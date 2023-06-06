package ru.otus.spring.dao;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.NoResultException;
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
import ru.otus.spring.repositories.BookRepositoryJpa;
import ru.otus.spring.repositories.CommentRepositoryJpa;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Репозитарий для работы с комментариями должен")
@DataJpaTest
@Import({BookRepositoryJpa.class, CommentRepositoryJpa.class})
public class CommentRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    private static final int EXPECTED_COMMENTS_COUNT = 3;
    private static final Long EXISTING_COMMENT_ID = 2L;

    private Comment EXPECTED_COMMENT;

    @PostConstruct
    private void prepareCommentTest(){
        EXPECTED_COMMENT = new Comment(
                bookRepository.getById(1),
                "TestComment5",
                "TestUser5"
        );
    }

    @DisplayName("возвращать ожидаемое количество комментариев в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        Long actualPersonsCount = commentRepositoryJpa.getAllComments().stream().count();
        assertThat(actualPersonsCount).isEqualTo(EXPECTED_COMMENTS_COUNT);
    }

    @DisplayName("возвращать комментарии по имени книги и ID")
    @Test
    void shouldReturnExpectedComment() {
        Long actualCommentsCount = (long) bookRepository.getByName("TestBookName_1").getComment().size();
        assertThat(actualCommentsCount).isEqualTo(2);
        Comment comment = commentRepositoryJpa.getCommentById(3L);
        assertThat(comment.getBook().getName()).isEqualTo("TestBookName_3");
    }

    @DisplayName("добавлять комментарий в БД")
    @Test
    void shouldInsertComment() {
        Comment expectedCOmment = new Comment(
                bookRepository.getById(1),
                "TestComment5",
                "TestUser5"
        );
        commentRepositoryJpa.addComment(expectedCOmment);
        Comment actualComment = commentRepositoryJpa.getCommentById(expectedCOmment.getId());
        assertThat(actualComment).isEqualTo(expectedCOmment);
    }

    @DisplayName("удалить комментарий в БД")
    @Test
    void shouldDeleteComment() {
        assertThatCode(() -> commentRepositoryJpa.getCommentById(EXISTING_COMMENT_ID))
                .doesNotThrowAnyException();

        commentRepositoryJpa.deleteComment(commentRepositoryJpa.getCommentById(EXISTING_COMMENT_ID));

        assertThat(commentRepositoryJpa.getCommentById(EXISTING_COMMENT_ID)).isNull();
    }
}
