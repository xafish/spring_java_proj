package ru.otus.spring.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import java.util.List;

@AllArgsConstructor
@Repository
public class CommentRepositoryJpa implements CommentRepository{
    @PersistenceContext
    private final EntityManager em;
    private static final String commentQueryStr = "select c from Comment c" +
            " LEFT JOIN FETCH c.book" +
            " LEFT JOIN FETCH c.book.author " +
            " LEFT JOIN FETCH c.book.genre ";
    @Override
    public List<Comment> getAllComments() {
        var query = em.createQuery(commentQueryStr, Comment.class);
        return query.getResultList();
    }

    @Override
    public Comment getCommentById(Long id) {
        return em.find(Comment.class,id);
    }

    @Override
    public void addComment(Comment comment) {
        em.persist(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        em.remove(comment);
    }
}
