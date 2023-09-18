package com.microservice.example.orderservices.utils;

import com.microservice.example.orderservices.dto.OrderItemDto;
import com.microservice.example.orderservices.dto.TorderDto;
import com.microservice.example.orderservices.entity.OrderItem;
import com.microservice.example.orderservices.entity.Torder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class OrderMapper {

    public static Torder dtoToTorder(TorderDto torderDto) {
        Torder torder = new Torder();
        torder.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItem> orderItemList = new ArrayList<>();

        log.info("Convert the order item to order item dto and add to to list");
        for (OrderItemDto orderItemDto : torderDto.getOrderItemDtoList()) {
            orderItemList.add(dtoToOrderItem(orderItemDto, torder));
        }
        torder.setOrderItemList(orderItemList);
        return torder;
    }

    public static OrderItem dtoToOrderItem(OrderItemDto orderItemDto, Torder torder) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductCode(orderItemDto.getProductCode());
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setUnitPrice(orderItemDto.getUnitPrice());
        orderItem.setTorder(torder);
        return orderItem;
    }

    public static TorderDto torderToDto(Torder torder) {
        TorderDto torderDto = new TorderDto();
        torderDto.setId(torder.getId());
        torderDto.setOrderNumber(torder.getOrderNumber());
        log.info("Convert the order item to order item dto");
        List<OrderItemDto> orderItemDtoList = torder.getOrderItemList().stream().
                map(OrderMapper::orderItemToDto).toList();
        torderDto.setOrderItemDtoList(orderItemDtoList);
        return torderDto;
    }

    public static OrderItemDto orderItemToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setProductCode(orderItem.getProductCode());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setUnitPrice(orderItem.getUnitPrice());
        return orderItemDto;
    }
}
