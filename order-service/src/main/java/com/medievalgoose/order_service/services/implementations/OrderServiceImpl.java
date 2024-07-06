package com.medievalgoose.order_service.services.implementations;

import com.medievalgoose.order_service.dtos.OrderEvent;
import com.medievalgoose.order_service.dtos.OrderProductRequestDto;
import com.medievalgoose.order_service.dtos.OrderRequestDto;
import com.medievalgoose.order_service.kafka.OrderProducer;
import com.medievalgoose.order_service.models.*;
import com.medievalgoose.order_service.repositories.*;
import com.medievalgoose.order_service.services.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final CustomerRepository customerRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final ProductRepository productRepository;
    private final OrderProducer orderProducer;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CustomerRepository customerRepository,
                            PaymentMethodRepository paymentMethodRepository,
                            OrderProductRepository orderProductRepository,
                            ProductRepository productRepository,
                            OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.orderProductRepository = orderProductRepository;
        this.productRepository = productRepository;
        this.orderProducer = orderProducer;
    }

    @Override
    public Order createOrder(OrderRequestDto orderRequestDto) {
        Optional<Customer> relevantCustomer = customerRepository.findById(orderRequestDto.getCustomerGuid());
        if (relevantCustomer.isEmpty()) {
            return null;
        }

        Optional<PaymentMethod> relevantPaymentMethod = paymentMethodRepository.findById(orderRequestDto.getPaymentMethodGuid());
        if (relevantPaymentMethod.isEmpty()) {
            return null;
        }

        Order newOrder = new Order();
        newOrder.setTimestamp(LocalDateTime.now());
        newOrder.setCustomer(relevantCustomer.get());
        newOrder.setPaymentMethod(relevantPaymentMethod.get());

        Order createdOrder = orderRepository.save(newOrder);

        // Assign the product details.
        List<String> productGuids = orderRequestDto.getOrderProducts()
                .stream()
                .map(OrderProductRequestDto::getProductGuid)
                .toList();

        Map<String, Integer> orderProductsMap = orderRequestDto.getOrderProducts()
                .stream()
                .collect(Collectors
                        .toMap(orderProd -> orderProd.getProductGuid(),
                                orderProd -> orderProd.getQuantity()));

        List<OrderProductRequestDto> validOrderProducts = new ArrayList<>();

        List<Product> relevantProducts = productRepository.findAllById(productGuids);

        // Check the validity of requested quantity and available stocks.
        for(Product product : relevantProducts) {
            int requestedQuantity = orderProductsMap.get(product.getGuid());

            if (product.getStock() >= requestedQuantity) {
                OrderProduct newOrderProduct = new OrderProduct();
                newOrderProduct.setOrder(createdOrder);
                newOrderProduct.setProduct(product);
                newOrderProduct.setQuantity(orderProductsMap.get(product.getGuid()));
                newOrderProduct.setPrice(product.getPrice() * requestedQuantity);

                orderProductRepository.save(newOrderProduct);

                // Modify the stock too.
                product.setStock(product.getStock() - requestedQuantity);
                productRepository.save(product);

                // Add to the list of valid items.
                validOrderProducts.add(new OrderProductRequestDto(product.getGuid(), requestedQuantity));
            }
        }

        // Publish the event.
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderGuid(createdOrder.getGuid());
        orderEvent.setOrderProducts(validOrderProducts);

        orderProducer.produceOrderEvent(orderEvent);

        return createdOrder;
    }
}
