package com.microservice.example.productservices.controller;

import com.microservice.example.productservices.dto.ProductDto;
import com.microservice.example.productservices.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value="api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto){
        log.info("Calling product service add product");
        try {
            ProductDto savedProductDto = productService.addProduct(productDto);
            return new ResponseEntity<>(savedProductDto, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping
    public ResponseEntity<Long> deleteProduct(@RequestParam(value="productCode") String productCode) {
        try {
            log.info("Calling product service delete product");
            Long noOfRecordDeleted = productService.deleteProduct(productCode);
            return new ResponseEntity<>(noOfRecordDeleted, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProduct() {
        log.info("Calling product service get all product");
        List <ProductDto> productDtoList = productService.getAllProduct();
        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }
    @PutMapping(value="/modifyprice")
    public ResponseEntity<Void> modifyPrice(@RequestParam(value="productCode") String productCode,
                                      @RequestParam(value="price")BigDecimal price) {
        try {
            productService.modifyPrice(productCode, price);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
