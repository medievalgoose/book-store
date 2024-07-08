package com.medievalgoose.product_search_service.services.implementations;

import com.medievalgoose.product_search_service.models.Book;
import com.medievalgoose.product_search_service.repositories.BookRepository;
import com.medievalgoose.product_search_service.services.BookService;
import jakarta.annotation.Nullable;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.cluster.ClusterOperations;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final ElasticsearchOperations elasticsearchOperations;

    public BookServiceImpl(BookRepository bookRepository, ElasticsearchOperations elasticsearchOperations) {
        this.bookRepository = bookRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public List<Book> GetAllBooks(@Nullable String title, long priceLowerBound, long priceUpperBound) {

        Criteria criteria = new Criteria();

        if (title != null) {
            criteria =  criteria.and("title").contains(title);
        }

        criteria =  criteria.and("price").between(priceLowerBound, priceUpperBound);

        Query query = new CriteriaQuery(criteria);

        SearchHits<Book> bookSearchHit = elasticsearchOperations.search(query, Book.class);
        List<Book> bookSearch = new ArrayList<>();

        for (SearchHit<Book> searchHit : bookSearchHit.getSearchHits()) {
            bookSearch.add(searchHit.getContent());
        }

        return bookSearch;
    }

    @Override
    public Book GetBook(String id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }
}
