package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@ShellComponent
public class DataManagerShell {

    private final BookDao bookDao;

    @ShellMethod(key = "get all", value = "Get all books")
    public String getAllBook() {
        AtomicReference<String> result = new AtomicReference<>("");

        bookDao.getAll().forEach(
            b-> result.set(result + (b.toString() + "\n"))
        );

        return result.get();
    }
    @ShellMethod(key = "get", value = "Get book by id")
    public Book getBookById(@ShellOption({"id", "u"}) Long id) {
        return bookDao.getById(id);
    }

    @ShellMethod(key = "delete", value = "Delete book by id from DB")
    public String deleteBook(@ShellOption({"id", "u"}) Long id) {
        bookDao.deleteById(id);
        return "the book with id \"" + id +"\" has been successfully deleted";
    }

    @ShellMethod(key = "set name", value = "Update book name by id from DB")
    public String setName(@ShellOption({"id", "u"}) Long id, @ShellOption({"name", "u"}) String name) {
        bookDao.updateNameById(id,name);
        return "the book with id \"" + id +"\" has been successfully updated";
    }

    @ShellMethod(key = "add book", value = "Add book id, name, author name, author lastname and genre name")
    public String addBook(@ShellOption({"id", "u"}) Long id,
                          @ShellOption({"bookName", "u"}) String bookName,
                          @ShellOption({"authorName", "u"}) String authorName,
                          @ShellOption({"authorLastName", "u"}) String authorLastName,
                          @ShellOption({"genreName", "u"}) String genreName) {

        Author author;
        try {
            author = bookDao.getAuthorByNameLastname(authorName, authorLastName);
        } catch (EmptyResultDataAccessException ex) {
            return "Author \"" + authorName + " " + authorLastName + "\" was not found";
        }

        Genre genre;
        try {
            genre = bookDao.getGenreByName(genreName);
        } catch (EmptyResultDataAccessException ex) {
            return "Genre with name \"" + genreName + "\" was not found";
        }

        Book book = new Book(id, bookName, author, genre);

        bookDao.insert(book);
        return "the book \n " + book + " \n has been successfully inserted";
    }


}
