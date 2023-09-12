package com.microservice.example.inventoryservices.utils;

import com.microservice.example.inventoryservices.dto.InventoryDto;
import com.microservice.example.inventoryservices.entity.Inventory;

public class InventoryMapper {

    public static Inventory dtoToInventory(InventoryDto inventoryDto) {
        Inventory inventory = new Inventory();
        inventory.setProductCode(inventoryDto.getProductCode());
        inventory.setQuantity(inventoryDto.getQuantity());
        return inventory;
    }

    public static InventoryDto inventoryToDto(Inventory inventory) {
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setProductCode(inventory.getProductCode());
        inventoryDto.setQuantity(inventory.getQuantity());
        return inventoryDto;
    }
}
