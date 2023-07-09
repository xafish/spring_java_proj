package ru.otus.spring.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "books")
public class Book {

    @Id
    private final Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_author")
    private final Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_genre")
    private final Genre genre;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_book")
    private List<Comment> comment;

    public Book(Long id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(name, book.name) && Objects.equals(author, book.author) && Objects.equals(genre, book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, genre);
    }
}
