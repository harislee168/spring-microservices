package com.microservices.example.orderservices.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TorderDto {

    private Long id;
    private String orderNumber;
    private List<OrderItemDto> orderItemDtoList;
}
