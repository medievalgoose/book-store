package com.medievalgoose.product_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto {
    private String title;
    private long price;
    private String synopsis;
    private int stock;
    private List<String> genreGuid;
}
