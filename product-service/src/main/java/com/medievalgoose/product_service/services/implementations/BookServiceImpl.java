package com.medievalgoose.product_service.services.implementations;

import com.medievalgoose.product_service.dtos.BookRequestDto;
import com.medievalgoose.product_service.dtos.ProductEvent;
import com.medievalgoose.product_service.kafka.ProductProducer;
import com.medievalgoose.product_service.models.Book;
import com.medievalgoose.product_service.models.BookGenre;
import com.medievalgoose.product_service.models.Genre;
import com.medievalgoose.product_service.repositories.BookGenreRepository;
import com.medievalgoose.product_service.repositories.BookRepository;
import com.medievalgoose.product_service.repositories.GenreRepository;
import com.medievalgoose.product_service.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookGenreRepository bookGenreRepository;

    private final GenreRepository genreRepository;

    private final ProductProducer productProducer;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           BookGenreRepository bookGenreRepository,
                           GenreRepository genreRepository,
                           ProductProducer productProducer) {
        this.bookRepository = bookRepository;
        this.bookGenreRepository = bookGenreRepository;
        this.genreRepository = genreRepository;
        this.productProducer = productProducer;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book createBook(BookRequestDto bookRequestDto) {
        // Convert the object.
        Book newBook = new Book();
        newBook.setTitle(bookRequestDto.getTitle());
        newBook.setPrice(bookRequestDto.getPrice());
        newBook.setSynopsis(bookRequestDto.getSynopsis());
        newBook.setStock(bookRequestDto.getStock());

        Book createdBook = bookRepository.save(newBook);

        // Get the genre
        List<Genre> relevantGenre = genreRepository.findAllById(bookRequestDto.getGenreGuid());
        if (relevantGenre.isEmpty()) {
            return null;
        }

        // Add genres
        for (Genre genre : relevantGenre) {
            BookGenre newBookGenre = new BookGenre();
            newBookGenre.setBook(createdBook);
            newBookGenre.setGenre(genre);

            bookGenreRepository.save(newBookGenre);
        }

        // Publish the event to kafka.
        ProductEvent productEvent = new ProductEvent();
        productEvent.setGuid(createdBook.getGuid());
        productEvent.setTitle(createdBook.getTitle());
        productEvent.setPrice(createdBook.getPrice());
        productEvent.setSynopsis(createdBook.getSynopsis());
        productEvent.setStock(createdBook.getStock());

        productEvent.setGenres(relevantGenre
                .stream()
                .map(Genre::getName).toList());

        productProducer.produceProductEvent(productEvent);

        return createdBook;
    }
}
