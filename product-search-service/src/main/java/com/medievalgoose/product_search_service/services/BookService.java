package com.medievalgoose.product_search_service.services;

import com.medievalgoose.product_search_service.models.Book;
import jakarta.annotation.Nullable;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    List<Book> GetAllBooks(@Nullable String title, Long priceLowerBound, Long priceUpperBound);
    Book GetBook(String id);
}
