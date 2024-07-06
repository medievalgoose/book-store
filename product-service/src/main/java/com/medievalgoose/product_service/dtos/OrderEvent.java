package com.medievalgoose.product_service.dtos;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderEvent {
    private String orderGuid;
    private List<OrderProductRequestDto> orderProducts;
}
