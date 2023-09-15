package com.microservice.example.inventoryservices.service;

import com.microservice.example.inventoryservices.dto.InventoryDto;
import com.microservice.example.inventoryservices.dto.response.CreateOrderVerificationResponse;
import com.microservice.example.inventoryservices.dto.response.ModifyQuantityResponse;

import java.util.List;
import java.util.Map;

public interface InventoryService {

    public InventoryDto addInventory(InventoryDto inventoryDto);
    public Long deleteInventoryByProductCode(String productCode);
    public ModifyQuantityResponse modifyQuantity(String productCode, int quantity);
    public List <InventoryDto> getAllInventory();
    public CreateOrderVerificationResponse createOrderVerification(Map<String, Integer> createOrderRequests);
}
