package ru.otus.spring.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;
import ru.otus.spring.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@Service
public class BookLibraryServiceImpl implements BookLibraryService{

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public String deleteBook(Long id) {
        bookRepository.deleteById(id);
        return "the book with id \"" + id +"\" has been successfully deleted";
    }

    @Override
    public String setName(Long id, String name) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setName(name);
            bookRepository.save(book);
        } else {
            return "can't find book with id \"" + id +"\"";
        }
        //bookRepository.updateNameById(id,name);
        return "the book with id \"" + id +"\" has been successfully updated";
    }

    @Override
    public String addBook(Long id,
                          String bookName,
                          String authorName,
                          String authorLastName,
                          String genreName) {

        Optional<Author> author = authorRepository.findByNameAndLastname(authorName, authorLastName);
        if (author.isEmpty()) return "Author \"" + authorName + " " + authorLastName + "\" was not found";

        Optional<Genre> genre;
        genre = genreRepository.findByName(genreName);
        if (genre.isEmpty()) return "Genre with name \"" + genreName + "\" was not found";

        Book book = new Book(id, bookName, author.get(), genre.get());

        bookRepository.save(book);
        return "the book \n " + book + " \n has been successfully inserted";
    }

    @Override
    @Transactional
    public String getAllComment() {
        AtomicReference<String> result = new AtomicReference<>("");

        commentRepository.findAll().forEach(
                b-> result.set(result + (b.toString() + "\n"))
        );

        return result.get();
    }

    @Override
    @Transactional
    public String getCommentsByBookName(String bookName) {
        AtomicReference<String> result = new AtomicReference<>("");
        Optional<Book> book =  bookRepository.findByName(bookName);

        book.ifPresent(value -> value.getComment().forEach(
                b -> result.set(result + (b.toString() + "\n"))
        ));
        return result.get();
    }

    @Override
    public String addComment(Long bookId,
                             String text,
                             String userName) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty())  return "Book \"" + bookId + "\" was not found";
        Comment comment = new Comment(book.get(), text, userName);
        book.get().getComment().add(comment);
        bookRepository.save(book.get());

        return "the comment \n " + comment + " \n has been successfully inserted";
    }

    @Override
    public String deleteComment(Long id) {
        Optional<Comment> comment;
        comment = commentRepository.findById(id);
        if (comment.isEmpty()) return "Comment with id \"" + id + "\" was not found";
        commentRepository.delete(comment.get());
        return "the comment \n " + comment + " \n has been successfully deleted";
    }


}
