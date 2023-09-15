package com.microservices.example.orderservices.services.impl;

import com.microservices.example.orderservices.dto.TorderDto;
import com.microservices.example.orderservices.dto.response.CreateOrderVerificationResponse;
import com.microservices.example.orderservices.dto.response.TorderDtoResponse;
import com.microservices.example.orderservices.entity.Torder;
import com.microservices.example.orderservices.repository.OrderRepository;
import com.microservices.example.orderservices.services.OrderService;
import com.microservices.example.orderservices.utils.OrderMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    @Override
    @CircuitBreaker(name="inventory", fallbackMethod="createOrderFallbackMethod")
    public TorderDtoResponse createOrder(TorderDto torderDto) {
        //first check the product inside the order items are all available in inventory and has enough quantity
        log.info("Create map consist of productCode as key and quantity as value");
        Map<String, Integer> createOrderRequest = torderDto.getOrderItemDtoList().stream()
                .collect(Collectors.toMap(orderItemDto -> orderItemDto.getProductCode(),
                        orderItemDto -> orderItemDto.getQuantity()));

        CreateOrderVerificationResponse createOrderVerificationResponse = webClientBuilder.build().patch()
                .uri("http://inventory-service/api/inventory/createorderverification")
                .contentType(MediaType.APPLICATION_JSON).bodyValue(createOrderRequest)
                        .retrieve().bodyToMono(CreateOrderVerificationResponse.class).block();

        if (createOrderVerificationResponse != null && createOrderVerificationResponse.isVerification()) {
            log.info("Inventory verification is successful");
            log.info("Convert the TorderDto to Torder");
            Torder torder = OrderMapper.dtoToTorder(torderDto);
            Torder savedTorder = orderRepository.save(torder);
            TorderDto savedTorderDto = OrderMapper.torderToDto(savedTorder);
            return new TorderDtoResponse(savedTorderDto, null);
        }
        else {
            return new TorderDtoResponse(null,
                    "Fail to create order because the quantity is more than available stock");
        }
    }

    @Override
    public List<TorderDto> getAllOrder() {
        log.info("Get all Torder from database");
        List <Torder> torderList = orderRepository.findAll();
        log.info("Convert Torder list to TorderDto list and return the list");
        return torderList.stream().map(OrderMapper::torderToDto).toList();
    }

    public TorderDtoResponse createOrderFallbackMethod(TorderDto torderDto, RuntimeException runtimeException) {
        log.info("Cannot add product, executing fallback method");
        return new TorderDtoResponse(null, "Something went wrong with Inventory service, please try again later");
    }

}