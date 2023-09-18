package com.microservice.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NotificationServicesApplication {

    public static void main (String [] args) {
        SpringApplication.run(NotificationServicesApplication.class, args);
    }
}
