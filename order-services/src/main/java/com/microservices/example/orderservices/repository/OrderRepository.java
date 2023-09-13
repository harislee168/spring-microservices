package com.microservices.example.orderservices.repository;

import com.microservices.example.orderservices.entity.Torder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository <Torder, Long> {
}
