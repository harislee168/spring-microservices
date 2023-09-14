package com.microservices.example.orderservices.services;

import com.microservices.example.orderservices.dto.TorderDto;

import java.util.List;

public interface OrderService {

    public TorderDto createOrder(TorderDto torderDto) throws Exception;
    public List<TorderDto> getAllOrder();
}
