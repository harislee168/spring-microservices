package com.microservice.example.orderservices.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TorderKafkaTopic {

    @Value(value="${spring.kafka.orderTopicName}")
    private String orderTopicName;

    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name(orderTopicName).build();
    }
}
