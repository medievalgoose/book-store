package com.medievalgoose.order_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medievalgoose.order_service.dtos.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);
    private NewTopic topic;
    private KafkaTemplate<String, String> kafkaTemplate;

    public OrderProducer(NewTopic topic, KafkaTemplate<String, String> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceOrderEvent(OrderEvent orderEvent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String orderEventString = objectMapper.writeValueAsString(orderEvent);

            Message<String> message = MessageBuilder
                    .withPayload(orderEventString)
                    .setHeader(KafkaHeaders.TOPIC, topic.name())
                    .build();

            kafkaTemplate.send(message);

            LOGGER.info(String.format("Order event published => %s", orderEvent.toString()));

        } catch (Exception ex) {
            LOGGER.error("Serialization error");
        }
    }

}
