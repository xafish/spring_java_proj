package ru.otus.spring.repositories;

import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> getAllComments();
    Comment getCommentById(Long id);
    void addComment(Comment comment);
    void deleteComment(Comment comment);
}
