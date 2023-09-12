package com.microservice.example.productservices.repository;

import com.microservice.example.productservices.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Long deleteByProductCode(@RequestParam(value = "productCode") String productCode);
}
