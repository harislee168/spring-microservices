package com.microservice.example.inventoryservices.service;

import com.microservice.example.inventoryservices.dto.InventoryDto;

import java.util.List;
import java.util.Map;

public interface InventoryService {

    public InventoryDto addInventory(InventoryDto inventoryDto);
    public Long deleteInventoryByProductCode(String productCode);
    public void modifyQuantity(String productCode, int quantity) throws Exception;
    public List <InventoryDto> getAllInventory();
    public Boolean createOrderVerification(Map<String, Integer> createOrderRequests) throws Exception;
}
