package com.medievalgoose.product_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medievalgoose.product_service.dtos.OrderEvent;
import com.medievalgoose.product_service.dtos.OrderProductRequestDto;
import com.medievalgoose.product_service.models.Book;
import com.medievalgoose.product_service.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    private final BookRepository bookRepository;

    public OrderConsumer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.consumer.topic.name}")
    public void consumeOrderEvent(String orderEventString) {
        try {
            LOGGER.info(String.format("Order event consumed => %s", orderEventString));

            ObjectMapper objectMapper = new ObjectMapper();
            OrderEvent orderEvent = objectMapper.readValue(orderEventString, OrderEvent.class);

            // Modify product stocks.
            List<String> bookGuids = orderEvent.getOrderProducts()
                    .stream()
                    .map(OrderProductRequestDto::getProductGuid)
                    .toList();

            Map<String, Integer> orderProductMap = orderEvent.getOrderProducts()
                    .stream()
                    .collect(Collectors.toMap(odp -> odp.getProductGuid(), odp -> odp.getQuantity()));

            List<Book> relevantBooks = bookRepository.findAllById(bookGuids);

            for (Book book : relevantBooks) {
                book.setStock(book.getStock() - orderProductMap.get(book.getGuid()));
                bookRepository.save(book);
            }
        } catch (Exception ex) {
            LOGGER.error("Deserialization error");
        }
    }
}
