package com.microservice.example.productservices.repository;

import com.microservice.example.productservices.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Long deleteByProductCode(@Param(value = "productCode") String productCode);
    Optional<Product> findByProductCode(@Param(value="productCode") String productCode);
}
