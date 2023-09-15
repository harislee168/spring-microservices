package com.microservice.example.productservices.dto.response;

import com.microservice.example.productservices.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllProductDtoResponse {

    private List <ProductDto> productDtoList;
    private String errorMessage;
}
