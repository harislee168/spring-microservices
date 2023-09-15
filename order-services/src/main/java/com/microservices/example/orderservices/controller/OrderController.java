package com.microservices.example.orderservices.controller;

import com.microservices.example.orderservices.dto.TorderDto;
import com.microservices.example.orderservices.dto.response.TorderDtoResponse;
import com.microservices.example.orderservices.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/createorder")
    public ResponseEntity<TorderDtoResponse> createOrder(@RequestBody TorderDto torderDto) {
        log.info("Calling create order in order service");
        TorderDtoResponse torderDtoResponse = orderService.createOrder(torderDto);
        return new ResponseEntity<>(torderDtoResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TorderDto>> getAllOrder() {
        log.info("Calling the getAllOrder function in order service");
        List<TorderDto> torderDtoList = orderService.getAllOrder();
        return new ResponseEntity<>(torderDtoList, HttpStatus.OK);
    }
}
