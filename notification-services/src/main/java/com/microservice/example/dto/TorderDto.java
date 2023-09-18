package com.microservice.example.dto;

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

    public String toString() {
        StringBuilder fullMessage = new StringBuilder("Order number is: " + orderNumber);
        int counter = 1;
        for (OrderItemDto orderItemDto: orderItemDtoList) {
            fullMessage.append("\n");
            fullMessage.append(counter);
            fullMessage.append(". Product code: ").append(orderItemDto.getProductCode());
            fullMessage.append(" Quantity: ").append(orderItemDto.getQuantity());
            fullMessage.append(" Price: ").append(orderItemDto.getUnitPrice());
            counter++;
        }

        return fullMessage.toString();
    }
}
