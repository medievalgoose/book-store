package com.medievalgoose.order_service.services;

import com.medievalgoose.order_service.dtos.OrderRequestDto;
import com.medievalgoose.order_service.models.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Order createOrder(OrderRequestDto orderRequestDto);
}
