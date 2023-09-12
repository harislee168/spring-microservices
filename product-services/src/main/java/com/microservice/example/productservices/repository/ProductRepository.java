package com.microservice.example.productservices.repository;

import com.microservice.example.productservices.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Long deleteByProductCode(String productCode);
}
