package com.medievalgoose.order_service.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "payment_methods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "guid")
    private String guid;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "paymentMethod")
    private Set<Order> orders;
}
