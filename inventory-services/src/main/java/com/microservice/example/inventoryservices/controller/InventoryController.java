package com.microservice.example.inventoryservices.controller;

import com.microservice.example.inventoryservices.dto.InventoryDto;
import com.microservice.example.inventoryservices.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryDto> addInventory(@RequestBody InventoryDto inventoryDto) {
        log.info("Calling inventory service add inventory");
        InventoryDto savedInventoryDto = inventoryService.addInventory(inventoryDto);
        return new ResponseEntity<>(savedInventoryDto, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteInventory(@RequestParam(value="productCode") String productCode) {
        log.info("Calling inventory service delete inventory");
        Long noOfRecordDeleted = inventoryService.deleteInventoryByProductCode(productCode);
        return new ResponseEntity<>(noOfRecordDeleted, HttpStatus.OK);
    }

    @PutMapping(value="/modifyquantity")
    public ResponseEntity<Void> modifyQuantity(@RequestParam(value="productCode") String productCode,
                                               @RequestParam(value="quantity") int quantity) {
        try {
            inventoryService.modifyQuantity(productCode, quantity);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<List<InventoryDto>> getAllInventory() {
        List<InventoryDto> inventoryDtoList = inventoryService.getAllInventory();
        return new ResponseEntity<>(inventoryDtoList, HttpStatus.OK);
    }
}
