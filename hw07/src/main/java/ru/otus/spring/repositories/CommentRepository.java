package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"book", "book.author", "book.genre"})
    List<Comment> findAll();

    /*List<Comment> getAllComments();
    Comment getCommentById(Long id);
    void addComment(Comment comment);
    void deleteComment(Comment comment);*/
}
