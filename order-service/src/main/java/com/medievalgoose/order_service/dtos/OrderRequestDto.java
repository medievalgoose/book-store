package com.medievalgoose.order_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderRequestDto {
    private String customerGuid;
    private String paymentMethodGuid;
    private List<OrderProductRequestDto> orderProducts;
}
