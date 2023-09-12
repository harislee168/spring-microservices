package com.microservice.example.productservices.service.impl;

import com.microservice.example.productservices.dto.ProductDto;
import com.microservice.example.productservices.entity.Product;
import com.microservice.example.productservices.repository.ProductRepository;
import com.microservice.example.productservices.service.ProductService;
import com.microservice.example.productservices.utils.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        log.info("Convert the product dto to product entity object");
        Product product = ProductMapper.dtoToProduct(productDto);
        log.info("Saved the product entity");
        Product savedProduct = productRepository.save(product);
        log.info("Return back the saved product as product dto");
        return ProductMapper.productToDto(savedProduct);
    }

    @Override
    public Long deleteProduct(String productCode) {
        log.info("Delete the product by productCode");
        return productRepository.deleteByProductCode(productCode);
    }
}
