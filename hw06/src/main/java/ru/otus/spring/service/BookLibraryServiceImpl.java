package ru.otus.spring.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;
import ru.otus.spring.repositories.GenreRepository;

import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@Service
public class BookLibraryServiceImpl implements BookLibraryService{

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    @Override
    public String getAllBook() {
        AtomicReference<String> result = new AtomicReference<>("");

        bookRepository.findAll().forEach(
                b-> result.set(result + (b.toString() + "\n"))
        );

        return result.get();
    }

    @Override
    public Book getBookById(@ShellOption({"id", "u"}) Long id) {
        return bookRepository.getById(id);
    }

    @Override
    @Transactional
    public String deleteBook(@ShellOption({"id", "u"}) Long id) {
        bookRepository.deleteById(id);
        return "the book with id \"" + id +"\" has been successfully deleted";
    }

    @Override
    @Transactional
    public String setName(@ShellOption({"id", "u"}) Long id, @ShellOption({"name", "u"}) String name) {
        bookRepository.updateNameById(id,name);
        return "the book with id \"" + id +"\" has been successfully updated";
    }

    @Override
    @Transactional
    public String addBook(@ShellOption({"id", "u"}) Long id,
                          @ShellOption({"bookName", "u"}) String bookName,
                          @ShellOption({"authorName", "u"}) String authorName,
                          @ShellOption({"authorLastName", "u"}) String authorLastName,
                          @ShellOption({"genreName", "u"}) String genreName) {

        Author author;
        try {
            author = authorRepository.getAuthorByNameLastname(authorName, authorLastName);
        } catch (EmptyResultDataAccessException ex) {
            return "Author \"" + authorName + " " + authorLastName + "\" was not found";
        }

        Genre genre;
        try {
            genre = genreRepository.getGenreByName(genreName);
        } catch (EmptyResultDataAccessException ex) {
            return "Genre with name \"" + genreName + "\" was not found";
        }

        Book book = new Book(id, bookName, author, genre);

        bookRepository.addBook(book);
        return "the book \n " + book + " \n has been successfully inserted";
    }

    @Override
    public String getAllComment() {
        AtomicReference<String> result = new AtomicReference<>("");

        commentRepository.getAllComments().forEach(
                b-> result.set(result + (b.toString() + "\n"))
        );

        return result.get();
    }

    @Override
    @Transactional
    public String getCommentsByBookName(@ShellOption({"bookName", "u"}) String bookName) {
        AtomicReference<String> result = new AtomicReference<>("");

        bookRepository.getByName(bookName).getComment().forEach(
                b-> result.set(result + (b.toString() + "\n"))
        );
        return result.get();
    }

    @Override
    @Transactional
    public String addComment(@ShellOption({"bookId", "u"}) Long bookId,
                             @ShellOption({"text", "u"}) String text,
                             @ShellOption({"userName", "u"}) String userName) {

        Book book;
        try {
            book = bookRepository.getById(bookId);
        } catch (EmptyResultDataAccessException ex) {
            return "Book \"" + bookId + "\" was not found";
        }
        Comment comment = new Comment(book, text, userName);
        commentRepository.addComment(comment);
        return "the comment \n " + comment + " \n has been successfully inserted";
    }

    @Override
    @Transactional
    public String deleteComment(@ShellOption({"id", "u"}) Long id) {
        Comment comment;
        try {
            comment = commentRepository.getCommentById(id);
        } catch (EmptyResultDataAccessException ex) {
            return "Comment with id \"" + id + "\" was not found";
        }
        commentRepository.deleteComment(comment);
        return "the comment \n " + comment + " \n has been successfully deleted";
    }

}
