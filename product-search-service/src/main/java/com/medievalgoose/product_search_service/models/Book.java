package com.medievalgoose.product_search_service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Document(indexName = "bookstore_products")
public class Book {

    @Id
    private String guid;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Long)
    private long price;

    @Field(type = FieldType.Text)
    private String synopsis;

    @Field(type = FieldType.Integer)
    private int stock;

    @Field
    private List<String> genres;
}
