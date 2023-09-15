package com.microservice.example.productservices.dto.response;

import com.microservice.example.productservices.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductDtoResponse {

    private ProductDto productDto;
    private String errorMessage;
}
