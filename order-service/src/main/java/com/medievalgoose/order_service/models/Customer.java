package com.medievalgoose.order_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @Column(name = "guid")
    private String guid;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;
}
