package com.medievalgoose.order_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "guid")
    private String guid;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "customer_guid")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "payment_method_guid")
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "order")
    private Set<OrderProduct> orderProducts;
}
