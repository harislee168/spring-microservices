package com.microservice.example.productservices.controller;

import com.microservice.example.productservices.dto.ProductDto;
import com.microservice.example.productservices.dto.response.AddProductDtoResponse;
import com.microservice.example.productservices.dto.response.AllProductDtoResponse;
import com.microservice.example.productservices.dto.response.DeleteProductDtoResponse;
import com.microservice.example.productservices.dto.response.ModifiedPriceResponse;
import com.microservice.example.productservices.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "api/product")
@RequiredArgsConstructor
@Slf4j
@RefreshScope
public class ProductController {

    private final ProductService productService;
    @Value("${spring.testmessage}")
    private String testMessage;

    @PostMapping
    public ResponseEntity<AddProductDtoResponse> addProduct(@RequestBody ProductDto productDto) {
        log.info("Calling product service add product");
        AddProductDtoResponse addProductDtoResponse = productService.addProduct(productDto);
        return new ResponseEntity<>(addProductDtoResponse, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<DeleteProductDtoResponse> deleteProduct(@RequestParam(value = "productCode") String productCode) {
        log.info("Calling product service delete product");
        DeleteProductDtoResponse deleteProductDtoResponse = productService.deleteProduct(productCode);
        return new ResponseEntity<>(deleteProductDtoResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<AllProductDtoResponse> getAllProduct() {
        log.info("Calling product service get all product");
        AllProductDtoResponse allProductDtoResponse = productService.getAllProduct();
        return new ResponseEntity<>(allProductDtoResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/modifyprice")
    public ResponseEntity<ModifiedPriceResponse> modifyPrice(@RequestParam(value = "productCode") String productCode,
                                                             @RequestParam(value = "price") BigDecimal price) {
        ModifiedPriceResponse modifiedPriceResponse = productService.modifyPrice(productCode, price);
        return new ResponseEntity<>(modifiedPriceResponse, HttpStatus.OK);
    }

    @GetMapping(value="/testmessage")
    public ResponseEntity<String> testMessage() {
        return new ResponseEntity<>("Hello " + testMessage, HttpStatus.OK);
    }
}
