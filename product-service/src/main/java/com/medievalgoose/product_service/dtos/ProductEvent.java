package com.medievalgoose.product_service.dtos;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEvent {
    private String guid;
    private String title;
    private long price;
    private String synopsis;
    private int stock;
    private List<String> genres;
}
