package com.microservice.example.inventoryservices.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderVerificationResponse {

    private boolean verification;
    private String errorMessage;
}
