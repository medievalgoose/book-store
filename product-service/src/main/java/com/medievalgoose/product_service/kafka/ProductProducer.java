package com.medievalgoose.product_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medievalgoose.product_service.dtos.ProductEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class ProductProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductProducer.class);

    private NewTopic topic;

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public ProductProducer(NewTopic topic, KafkaTemplate<String, String> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceProductEvent(ProductEvent productEvent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String productEventString = objectMapper.writeValueAsString(productEvent);

            Message<String> message = MessageBuilder
                    .withPayload(productEventString)
                    .setHeader(KafkaHeaders.TOPIC, topic.name())
                    .build();

            kafkaTemplate.send(message);

            LOGGER.info(String.format("Product event published => %s", productEvent.toString()));
        } catch (Exception ex) {
            LOGGER.error("Product event failed to publish.");
        }

    }
}
