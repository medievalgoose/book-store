package com.medievalgoose.customer_service.services;

import com.medievalgoose.customer_service.models.Customer;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    Customer createCustomer(Customer customer);
}
