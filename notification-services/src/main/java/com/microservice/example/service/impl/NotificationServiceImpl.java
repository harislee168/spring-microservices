package com.microservice.example.service.impl;

import com.microservice.example.dto.TorderDto;
import com.microservice.example.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @KafkaListener(topics="${spring.kafka.orderTopicName}")
    @Override
    public void handleOrderMessage(TorderDto torderDto) {
        //Alternatively you can use this to send SMS, Email, or persist to database
        //But in this example I will just print out the order

        log.info("Received order message");
        log.info(torderDto.toString());
    }
}
