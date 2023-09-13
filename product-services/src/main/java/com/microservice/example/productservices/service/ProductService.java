package com.microservice.example.productservices.service;

import com.microservice.example.productservices.dto.ProductDto;

import java.util.List;

public interface ProductService {

    public ProductDto addProduct(ProductDto productDto) throws Exception;
    public Long deleteProduct(String productCode);
    public List <ProductDto> getAllProduct();
}
