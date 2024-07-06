package com.medievalgoose.order_service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "guid")
    private String guid;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private long price;

    @Column(name = "stock")
    private int stock;
}
