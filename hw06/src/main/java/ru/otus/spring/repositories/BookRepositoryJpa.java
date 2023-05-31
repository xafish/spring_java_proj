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

    private static String bookQueryStr = "select b from Book b" +
            " LEFT JOIN FETCH b.author " +
            " LEFT JOIN FETCH b.genre ";

    @Override
    @Transactional
    public List<Book> findAll() {
        var query = em.createQuery(bookQueryStr, Book.class);
        return query.getResultList();
    }

    @Override
    public Book getById(long id) {
        var query = em.createQuery(bookQueryStr.concat("where b.id = :id"), Book.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(long id) {
        var query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
    @Override
    public void updateNameById(Long id, String name) {
        var query = em.createQuery("update Book b set b.name = :name where b.id = :id");
        query.setParameter("name", name);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void addBook(Book book) {
        em.merge(book);
    }
}
