package com.medievalgoose.customer_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medievalgoose.customer_service.dtos.CustomerEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class CustomerProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerProducer.class);

    private final NewTopic topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public CustomerProducer(NewTopic topic, KafkaTemplate<String, String> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceCustomerEvent(CustomerEvent customerEvent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String customerEventString = objectMapper.writeValueAsString(customerEvent);

            Message<String> message = MessageBuilder
                    .withPayload(customerEventString)
                    .setHeader(KafkaHeaders.TOPIC, topic.name())
                    .build();

            kafkaTemplate.send(message);

            LOGGER.info(String.format("Customer event published => %s", customerEvent.toString()));
        } catch (Exception ex) {
            LOGGER.error("Serialize error");
        }
    }
}
