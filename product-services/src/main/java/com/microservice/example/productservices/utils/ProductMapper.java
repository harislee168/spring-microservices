package com.microservice.example.productservices.utils;

import com.microservice.example.productservices.dto.ProductDto;
import com.microservice.example.productservices.entity.Product;

import java.math.BigDecimal;

public class ProductMapper {

    public static ProductDto productToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setProductCode(product.getProductCode());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    public static Product dtoToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setProductCode(productDto.getProductCode());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        return product;
    }
}
