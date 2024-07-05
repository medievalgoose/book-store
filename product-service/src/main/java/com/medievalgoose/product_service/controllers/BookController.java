package com.medievalgoose.product_service.controllers;

import com.medievalgoose.product_service.dtos.BookRequestDto;
import com.medievalgoose.product_service.models.Book;
import com.medievalgoose.product_service.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookRequestDto bookRequestDto) {
        Book createdBook = bookService.createBook(bookRequestDto);
        return ResponseEntity.ok(createdBook);
    }
}
