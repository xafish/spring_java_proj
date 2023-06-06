package ru.otus.spring.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

import java.util.List;

@Repository
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    public BookRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    private static final String bookQueryStr = "select b from Book b" +
            " LEFT JOIN FETCH b.author " +
            " LEFT JOIN FETCH b.genre ";

    @Override
    public List<Book> findAll() {
        var query = em.createQuery(bookQueryStr, Book.class);
        return query.getResultList();
    }

    @Override
    public Book getById(long id) {
        return em.find(Book.class,id);
    }

    @Override
    public Book getByName(String name) {
        var query = em.createQuery(bookQueryStr + " where b.name = :name", Book.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(long id) {
        em.remove(getById(id));
    }
    @Override
    public void updateNameById(Long id, String name) {
        Book book = getById(id);
        if (book != null) {
            book.setName(name);
            em.persist(book);
        }
    }

    @Override
    public void addBook(Book book) {
        em.persist(book);
    }
}
