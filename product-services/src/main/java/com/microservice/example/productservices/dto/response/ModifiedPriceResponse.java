package com.microservice.example.productservices.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModifiedPriceResponse {

    private boolean isPriceModified;
    private String errorMessage;
}
