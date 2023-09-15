package com.microservice.example.inventoryservices.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyQuantityResponse {

    private boolean isQuantityModified;
    private String errorMessage;
}
