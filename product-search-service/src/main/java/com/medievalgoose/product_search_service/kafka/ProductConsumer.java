package com.medievalgoose.product_search_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medievalgoose.product_search_service.dtos.ProductEvent;
import com.medievalgoose.product_search_service.models.Book;
import com.medievalgoose.product_search_service.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductConsumer.class);

    private final BookRepository bookRepository;

    public ProductConsumer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.consumer.product.topic.name}")
    public void consumeProductEvent(String productEventString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ProductEvent productEvent = objectMapper.readValue(productEventString, ProductEvent.class);

            LOGGER.info(String.format("Product event consumed => %s", productEvent.toString()));

            Book book = new Book();
            book.setGuid(productEvent.getGuid());
            book.setTitle(productEvent.getTitle());
            book.setSynopsis(productEvent.getSynopsis());
            book.setStock(productEvent.getStock());
            book.setPrice(productEvent.getPrice());
            book.setGenres(productEvent.getGenres());

            bookRepository.save(book);

        } catch (Exception ex) {
            LOGGER.error("Deserialization error");
        }
    }

}
