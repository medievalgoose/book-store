package com.medievalgoose.product_search_service.repositories;

import com.medievalgoose.product_search_service.models.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends ElasticsearchRepository<Book, String> {
    List<Book> findByTitle(String title);
}
