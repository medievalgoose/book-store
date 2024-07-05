package com.medievalgoose.customer_service.services.implementations;

import com.medievalgoose.customer_service.dtos.CustomerEvent;
import com.medievalgoose.customer_service.kafka.CustomerProducer;
import com.medievalgoose.customer_service.models.Customer;
import com.medievalgoose.customer_service.repositories.CustomerRepository;
import com.medievalgoose.customer_service.services.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerProducer customerProducer;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerProducer customerProducer) {
        this.customerRepository = customerRepository;
        this.customerProducer = customerProducer;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer createdCustomer = customerRepository.save(customer);

        // Publish the event to kafka.
        CustomerEvent customerEvent = new CustomerEvent();
        customerEvent.setGuid(createdCustomer.getGuid());
        customerEvent.setName(createdCustomer.getName());

        customerProducer.produceCustomerEvent(customerEvent);

        return createdCustomer;
    }
}
