package com.medievalgoose.order_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderProductRequestDto {
    private String productGuid;
    private int quantity;
}
