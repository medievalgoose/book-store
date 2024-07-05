package com.medievalgoose.product_service.services;

import com.medievalgoose.product_service.dtos.BookRequestDto;
import com.medievalgoose.product_service.models.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    List<Book> getAllBooks();
    Book createBook(BookRequestDto bookRequestDto);
}
