package com.microservice.example.inventoryservices.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {

    private Long id;
    private String productCode;
    private int quantity;
}
