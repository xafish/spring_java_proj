package ru.otus.spring.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {
    private final JdbcOperations jdbc;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(JdbcOperations jdbcOperations, NamedParameterJdbcOperations namedParameterJdbcOperations)
    {
        this.jdbc = jdbcOperations;
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        Integer count = jdbc.queryForObject("select count(*) from books", Integer.class);
        return count == null? 0: count;
    }

    @Override
    public void insert(Book book) {
        jdbc.update("insert into books (id, name, id_author, id_genre) values (?, ?, ?, ?)", book.getId(), book.getName(), book.getAuthor().getId(), book.getGenre().getId());
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        String GET_BOOK_BY_ID_SQL = "select b.id id_book, b.name name_book, b.id_author, a.name name_author, a.lastname lastname_author, b.id_genre, g.name name_genre " +
                "from books b " +
                "left join authors a on a.id = b.id_author " +
                "left join genres g on g.id = b.id_genre " +
                "where b.id = :id";
        return namedParameterJdbcOperations.queryForObject(
                GET_BOOK_BY_ID_SQL, params, new BookMapper()
        );
    }

    @Override
    public List<Book> getAll() {
        String GET_BOOK_SQL = "select b.id id_book, b.name name_book, b.id_author, a.name name_author, a.lastname lastname_author, b.id_genre, g.name name_genre " +
                "from books b " +
                "left join authors a on a.id = b.id_author " +
                "left join genres g on g.id = b.id_genre";
        return jdbc.query(GET_BOOK_SQL, new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from books where id = :id", params
        );
    }
    @Override
    public void updateNameById(long id, String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("name", name);
        namedParameterJdbcOperations.update(
                "update books set name = :name where id = :id", params
        );
    }

    @Override
    public Author getAuthorByNameLastname(String name, String lastName) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("lastname", lastName);
        String GET_AUTHOR_BY_NAME_SQL = "select id id_author, name name_author, lastname lastname_author " +
                "from authors " +
                "where name = :name and lastname = :lastname";
        return namedParameterJdbcOperations.queryForObject(GET_AUTHOR_BY_NAME_SQL, params, new AuthorMapper());
    }

    @Override
    public Genre getGenreByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        String GET_GENRE_BY_NAME_SQL = "select id id_genre, name name_genre " +
                "from genres " +
                "where name = :name";
        return namedParameterJdbcOperations.queryForObject(GET_GENRE_BY_NAME_SQL, params, new GenreMapper());
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long idBook = resultSet.getLong("id_book");
            String nameBook = resultSet.getString("name_book");
            long idAuthor = resultSet.getLong("id_author");
            String nameAuthor = resultSet.getString("name_author");
            String lastnameAuthor = resultSet.getString("lastname_author");
            long idGenre = resultSet.getLong("id_genre");
            String nameGenre = resultSet.getString("name_genre");
            return new Book(idBook, nameBook, new Author(idAuthor, nameAuthor, lastnameAuthor),new Genre(idGenre, nameGenre));
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long idAuthor = resultSet.getLong("id_author");
            String nameAuthor = resultSet.getString("name_author");
            String lastnameAuthor = resultSet.getString("lastname_author");
            return new Author(idAuthor, nameAuthor, lastnameAuthor);
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long idGenre = resultSet.getLong("id_genre");
            String nameGenre = resultSet.getString("name_genre");
            return new Genre(idGenre, nameGenre);
        }
    }
}
