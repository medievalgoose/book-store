package com.medievalgoose.product_service.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "guid")
    private String guid;

    @NonNull
    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private long price;

    @Column(name = "synopsis")
    private String synopsis;

    @Column(name = "stock")
    private int stock;

    @OneToMany(mappedBy = "book")
    private Set<BookGenre> bookGenres;
}
