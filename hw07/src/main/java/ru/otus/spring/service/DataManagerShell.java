package ru.otus.spring.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@ShellComponent
public class DataManagerShell {

    private final BookLibraryService bookLibraryService;

    @ShellMethod(key = "get all", value = "Get all books")
    public String getAllBook() {
        AtomicReference<String> result = new AtomicReference<>("");

        bookLibraryService.getAllBook().forEach(
                b-> result.set(result + (b.toString() + "\n"))
        );
        return result.get();
    }

    @ShellMethod(key = "get", value = "Get book by id")
    public String getBookById(@ShellOption({"id", "u"}) Long id) {
        return bookLibraryService.getBookById(id).toString();
    }

    @ShellMethod(key = "delete", value = "Delete book by id from DB")
    public String deleteBook(@ShellOption({"id", "u"}) Long id) {
        return bookLibraryService.deleteBook(id);
    }

    @ShellMethod(key = "set name", value = "Update book name by id from DB")
    public String setName(@ShellOption({"id", "u"}) Long id, @ShellOption({"name", "u"}) String name) {
        return bookLibraryService.setName(id,name);
    }
    @ShellMethod(key = "add book", value = "Add book id, name, author name, author lastname and genre name")
    public String addBook(@ShellOption({"id", "u"}) Long id,
                          @ShellOption({"bookName", "u"}) String bookName,
                          @ShellOption({"authorName", "u"}) String authorName,
                          @ShellOption({"authorLastName", "u"}) String authorLastName,
                          @ShellOption({"genreName", "u"}) String genreName) {
        return bookLibraryService.addBook(id,bookName,authorName,authorLastName,genreName);
    }

    @ShellMethod(key = "get comments", value = "Get all comment")
    public String getAllComment() {
        return bookLibraryService.getAllComment();
    }

    @ShellMethod(key = "get comment", value = "Get all comment")
    public String getCommentsByBookName(@ShellOption({"bookName", "u"}) String bookName) {
        return bookLibraryService.getCommentsByBookName(bookName);
    }

    @ShellMethod(key = "add comment", value = "Add comment id, text, userName")
    public String addComment(@ShellOption({"bookId", "u"}) Long bookId,
                          @ShellOption({"text", "u"}) String text,
                          @ShellOption({"userName", "u"}) String userName) {
        return bookLibraryService.addComment(bookId,text,userName);
    }

    @ShellMethod(key = "delete comment", value = "Add book id, name, author name, author lastname and genre name")
    public String deleteComment(@ShellOption({"id", "u"}) Long id) {
        return bookLibraryService.deleteComment(id);
    }

}
