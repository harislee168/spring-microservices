package com.microservice.example.orderservices.repository;

import com.microservice.example.orderservices.entity.Torder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository <Torder, Long> {
}
