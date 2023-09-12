package com.microservice.example.inventoryservices.service;

import com.microservice.example.inventoryservices.dto.InventoryDto;

public interface InventoryService {

    public InventoryDto addInventory(InventoryDto inventoryDto);
}
