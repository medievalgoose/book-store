package com.medievalgoose.product_search_service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Document(indexName = "bookstore_products")
public class Book {

    @Id
    private String guid;

    private String title;

    private long price;

    private String synopsis;

    private int stock;

    private List<String> genres;
}
