package com.medievalgoose.order_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medievalgoose.order_service.dtos.ProductEvent;
import com.medievalgoose.order_service.models.Product;
import com.medievalgoose.order_service.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductConsumer.class);

    private final ProductRepository productRepository;

    public ProductConsumer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
    topics = "${spring.kafka.product.topic.name}")
    public void consumeProductEvent(String productEventString) {
        try {
            LOGGER.info(String.format("Product event consumed => %s", productEventString.toString()));

            ObjectMapper objectMapper = new ObjectMapper();
            ProductEvent productEvent = objectMapper.readValue(productEventString, ProductEvent.class);

            // Save to database
            Product product = new Product();
            product.setGuid(productEvent.getGuid());
            product.setTitle(productEvent.getTitle());
            product.setPrice(productEvent.getPrice());
            product.setStock(productEvent.getStock());

            productRepository.save(product);
        } catch (Exception ex) {
            LOGGER.error("Deserialize error");
        }

    }
}
