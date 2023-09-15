package com.microservice.example.inventoryservices.service.impl;

import com.microservice.example.inventoryservices.dto.InventoryDto;
import com.microservice.example.inventoryservices.dto.response.CreateOrderVerificationResponse;
import com.microservice.example.inventoryservices.dto.response.ModifyQuantityResponse;
import com.microservice.example.inventoryservices.entity.Inventory;
import com.microservice.example.inventoryservices.repository.InventoryRepository;
import com.microservice.example.inventoryservices.service.InventoryService;
import com.microservice.example.inventoryservices.utils.InventoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

    @Override
    public ModifyQuantityResponse modifyQuantity(String productCode, int quantity) {
        log.info("Get inventory by product code");
        Optional <Inventory> optionalInventory = inventoryRepository.findByProductCode(productCode);
        ModifyQuantityResponse modifyQuantityResponse = new ModifyQuantityResponse(true, null);
        if (optionalInventory.isPresent()) {
            log.info("Set the quantity and save");
            Inventory inventory = optionalInventory.get();
            inventory.setQuantity(quantity);
            inventoryRepository.save(inventory);
        }
        else {
            modifyQuantityResponse.setQuantityModified(false);
            modifyQuantityResponse.setErrorMessage("Invalid product code: Inventory not found");
        }
        return modifyQuantityResponse;
    }

    @Override
    public List<InventoryDto> getAllInventory() {
        List <Inventory> inventoryList = inventoryRepository.findAll();
        return inventoryList.stream().map(InventoryMapper::inventoryToDto).toList();
    }

    public CreateOrderVerificationResponse createOrderVerification(Map <String, Integer> createOrderRequests) {
        log.info("Retrieve all product codes (key)");
        Set<String> productCodes = createOrderRequests.keySet();
        log.info("Get inventory by product codes");
        List <Inventory> inventoryList = inventoryRepository.findByProductCodeIn(productCodes);
        log.info("Check the order quantity must be less than or equal to quantity in database");
        CreateOrderVerificationResponse createOrderVerificationResponse = new CreateOrderVerificationResponse(true, null);

        for (Inventory inventory: inventoryList) {
            int orderQuantity = createOrderRequests.get(inventory.getProductCode());
            if (orderQuantity > inventory.getQuantity()) {
                createOrderVerificationResponse.setVerification(false);
                createOrderVerificationResponse.setErrorMessage("Order quantity for product " + inventory.getProductCode()
                        + " is more than what's available");
                return createOrderVerificationResponse;
            }
        }

        //nned to divide the loop because the data is persist and quantity is reduced if place the reduce quantity in the first loop
        log.info("Passed the quantity check, reduce the quantity and save the data");
        for (Inventory inventory: inventoryList) {
            int orderQuantity = createOrderRequests.get(inventory.getProductCode());
            inventory.setQuantity(inventory.getQuantity() - orderQuantity);
        }
        inventoryRepository.saveAll(inventoryList);
        return createOrderVerificationResponse;
    }
}
