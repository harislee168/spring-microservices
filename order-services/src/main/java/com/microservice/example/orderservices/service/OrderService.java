package com.microservice.example.orderservices.service;

import com.microservice.example.orderservices.dto.TorderDto;
import com.microservice.example.orderservices.dto.response.TorderDtoResponse;

import java.util.List;

public interface OrderService {

    public TorderDtoResponse createOrder(TorderDto torderDto);
    public List<TorderDto> getAllOrder();
}
