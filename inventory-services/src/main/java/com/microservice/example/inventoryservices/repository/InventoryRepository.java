package com.microservice.example.inventoryservices.repository;

import com.microservice.example.inventoryservices.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Long deleteByProductCode(@Param(value="productCode") String productCode);
    Optional <Inventory> findByProductCode(@Param(value="productCode") String productCode);
    List <Inventory> findByProductCodeIn(@Param(value="productCodes") Set<String> productCodes);

}