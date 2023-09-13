package com.microservice.example.inventoryservices.service.impl;

import com.microservice.example.inventoryservices.dto.InventoryDto;
import com.microservice.example.inventoryservices.entity.Inventory;
import com.microservice.example.inventoryservices.repository.InventoryRepository;
import com.microservice.example.inventoryservices.service.InventoryService;
import com.microservice.example.inventoryservices.utils.InventoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public InventoryDto addInventory(InventoryDto inventoryDto) {
        log.info("Convert the inventory dto to inventory entity object");
        Inventory inventory = InventoryMapper.dtoToInventory(inventoryDto);
        log.info("Saved the inventory entity");
        Inventory savedInventory = inventoryRepository.save(inventory);
        log.info("Return back the saved inventory as inventory dto");
        return InventoryMapper.inventoryToDto(savedInventory);
    }

    @Override
    public Long deleteInventoryByProductCode(String productCode) {
        log.info("Delete inventory by product code");
        return inventoryRepository.deleteByProductCode(productCode);
    }
}
