package com.medievalgoose.order_service.dtos;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class CustomerEvent {
    private String guid;
    private String name;
}
