package com.microservices.example.orderservices.services.impl;

import com.microservices.example.orderservices.dto.TorderDto;
import com.microservices.example.orderservices.entity.Torder;
import com.microservices.example.orderservices.repository.OrderRepository;
import com.microservices.example.orderservices.services.OrderService;
import com.microservices.example.orderservices.utils.OrderMapper;
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
    public TorderDto createOrder(TorderDto torderDto) throws Exception {
        //first check the product inside the order items are all available in inventory and has enough quantity
        log.info("Create map consist of productCode as key and quantity as value");
        Map<String, Integer> createOrderRequest = torderDto.getOrderItemDtoList().stream()
                .collect(Collectors.toMap(orderItemDto -> orderItemDto.getProductCode(),
                        orderItemDto -> orderItemDto.getQuantity()));

        Boolean verification = webClientBuilder.build().patch()
                .uri("http://inventory-service/api/inventory/createorderverification")
                .contentType(MediaType.APPLICATION_JSON).bodyValue(createOrderRequest)
                        .retrieve().bodyToMono(Boolean.class).block();

        if (verification != null && verification) {
            log.info("Inventory verification is successful");
            log.info("Convert the TorderDto to Torder");
            Torder torder = OrderMapper.dtoToTorder(torderDto);
            Torder savedTorder = orderRepository.save(torder);
            return OrderMapper.torderToDto(savedTorder);
        }
        else {
            throw new Exception("Inventory verification fail");
        }
    }

    @Override
    public List<TorderDto> getAllOrder() {
        log.info("Get all Torder from database");
        List <Torder> torderList = orderRepository.findAll();
        log.info("Convert Torder list to TorderDto list and return the list");
        return torderList.stream().map(OrderMapper::torderToDto).toList();
    }


}