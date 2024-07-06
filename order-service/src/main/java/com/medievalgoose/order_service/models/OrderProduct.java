package com.medievalgoose.order_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "order_products")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "guid")
    private String guid;

    @ManyToOne
    @JoinColumn(name = "order_guid")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_guid")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private long price;
}
