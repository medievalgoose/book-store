package com.medievalgoose.product_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "book_genres")
public class BookGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "guid")
    private String guid;

    @ManyToOne
    @JoinColumn(name = "book_guid")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "genre_guid")
    private Genre genre;
}
