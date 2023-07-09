package ru.otus.spring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "comments")
public class Comment {
    public Comment(Book book, String text, String userName) {
        this.id=null;
        this.book=book;
        this.text=text;
        this.userName=userName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_book")
    private final Book book;

    @Column(name = "text")
    private final String text;

    @Column(name = "user_name")
    private final String userName;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", book=" + book +
                ", text='" + text + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
