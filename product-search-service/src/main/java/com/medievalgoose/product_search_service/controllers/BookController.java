package com.medievalgoose.product_search_service.controllers;

import com.medievalgoose.product_search_service.models.Book;
import com.medievalgoose.product_search_service.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books-search")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<?> GetAllBooks(@RequestParam(required = false) String title,
                                         @RequestParam(required = false) Long priceLowerBound,
                                         @RequestParam(required = false) Long priceUpperBound) {
        return ResponseEntity.ok(bookService.GetAllBooks(title, priceLowerBound, priceUpperBound));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetBook(@PathVariable String id) {
        Book relevantBook = bookService.GetBook(id);
        if (relevantBook == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(relevantBook);
    }
}
