package com.microservices.example.orderservices.services;

import com.microservices.example.orderservices.dto.TorderDto;
import com.microservices.example.orderservices.dto.response.TorderDtoResponse;

import java.util.List;

public interface OrderService {

    public TorderDtoResponse createOrder(TorderDto torderDto);
    public List<TorderDto> getAllOrder();
}
