package com.medievalgoose.product_service.dtos;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderProductRequestDto {
    private String productGuid;
    private int quantity;
}
