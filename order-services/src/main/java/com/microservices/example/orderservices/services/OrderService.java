package com.microservices.example.orderservices.services;

import com.microservices.example.orderservices.dto.TorderDto;

public interface OrderService {

    public TorderDto createOrder(TorderDto torderDto) throws Exception;
}
