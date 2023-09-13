package com.microservice.example.productservices.service;

import com.microservice.example.productservices.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    public ProductDto addProduct(ProductDto productDto) throws Exception;
    public Long deleteProduct(String productCode) throws Exception;
    public List <ProductDto> getAllProduct();
    public void modifyPrice(String productCode, BigDecimal price) throws Exception;
}
