package com.microservices.example.orderservices.dto.response;

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
