package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookLibraryService {

    List<Book> getAllBook();

    Book getBookById(Long id);

    String deleteBook(Long id);

    String setName(Long id, String name);

    String addBook(Long id,
                   String bookName,
                   String authorName,
                   String authorLastName,
                   String genreName);

    String getAllComment();

    String getCommentsByBookName(String bookName);

    String addComment(Long bookId,
                      String text,
                      String userName);

    String deleteComment(Long id);

}
