package com.microservice.example.inventoryservices.repository;

import com.microservice.example.inventoryservices.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Long deleteByProductCode(@RequestParam(value="productCode") String productCode);
}
