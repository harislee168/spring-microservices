package com.microservice.example.inventoryservices.repository;

import com.microservice.example.inventoryservices.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
