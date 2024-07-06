package com.medievalgoose.order_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medievalgoose.order_service.dtos.CustomerEvent;
import com.medievalgoose.order_service.models.Customer;
import com.medievalgoose.order_service.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerConsumer.class);

    private final CustomerRepository customerRepository;

    public CustomerConsumer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.customer.topic.name}")
    public void consumeCustomerEvent(String customerEventString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CustomerEvent customerEvent = objectMapper.readValue(customerEventString, CustomerEvent.class);

            LOGGER.info(String.format("Customer event consumed => %s", customerEventString));

            // Save to db.
            Customer customer = new Customer();
            customer.setGuid(customerEvent.getGuid());
            customer.setName(customerEvent.getName());

            customerRepository.save(customer);
        } catch (Exception ex) {
            LOGGER.error("Deserialization error");
        }
    }
}
