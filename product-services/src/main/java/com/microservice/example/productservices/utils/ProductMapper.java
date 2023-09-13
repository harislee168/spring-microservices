package com.microservice.example.productservices.utils;

import com.microservice.example.productservices.dto.InventoryDto;
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

    public static InventoryDto productDtoToInventoryDto(ProductDto productDto) {
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setProductCode(productDto.getProductCode());
        inventoryDto.setQuantity(productDto.getQuantity());
        return inventoryDto;
    }

    public static ProductDto productInventoryDtoToDto(Product product, InventoryDto inventoryDto) {
        ProductDto productDto = productToDto(product);
        productDto.setQuantity(inventoryDto.getQuantity());
        return productDto;
    }
}
