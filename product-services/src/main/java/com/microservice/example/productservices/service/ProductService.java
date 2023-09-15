package com.microservice.example.productservices.service;

import com.microservice.example.productservices.dto.ProductDto;
import com.microservice.example.productservices.dto.response.AddProductDtoResponse;
import com.microservice.example.productservices.dto.response.AllProductDtoResponse;
import com.microservice.example.productservices.dto.response.DeleteProductDtoResponse;
import com.microservice.example.productservices.dto.response.ModifiedPriceResponse;

import java.math.BigDecimal;

public interface ProductService {

    public AddProductDtoResponse addProduct(ProductDto productDto);
    public DeleteProductDtoResponse deleteProduct(String productCode);
    public AllProductDtoResponse getAllProduct();
    public ModifiedPriceResponse modifyPrice(String productCode, BigDecimal price);
}
