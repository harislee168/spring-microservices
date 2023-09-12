package com.microservice.example.productservices.controller;

import com.microservice.example.productservices.dto.ProductDto;
import com.microservice.example.productservices.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        log.info("Calling product service add product");
        ProductDto savedProductDto = productService.addProduct(productDto);
        return new ResponseEntity<>(savedProductDto, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteProduct(@RequestParam(value="productCode")String productCode) {
        log.info("Calling product service delete product");
        Long noOfRecordDeleted = productService.deleteProduct(productCode);
        return new ResponseEntity<>(noOfRecordDeleted, HttpStatus.OK);
    }
}
