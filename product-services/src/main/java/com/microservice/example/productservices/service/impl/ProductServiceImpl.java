package com.microservice.example.productservices.service.impl;

import com.microservice.example.productservices.dto.InventoryDto;
import com.microservice.example.productservices.dto.ProductDto;
import com.microservice.example.productservices.entity.Product;
import com.microservice.example.productservices.repository.ProductRepository;
import com.microservice.example.productservices.service.ProductService;
import com.microservice.example.productservices.utils.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final WebClient webClient;

    @Override
    public ProductDto addProduct(ProductDto productDto) throws Exception {
        log.info("Convert the product dto to product entity object");
        Product product = ProductMapper.dtoToProduct(productDto);
        log.info("Create the Inventory dto");
        InventoryDto inventoryDto = ProductMapper.productDtoToInventoryDto(productDto);

        log.info("Saved the product entity");
        Product savedProduct = productRepository.save(product);

        log.info("Call inventory controller to record the inventory of the new product");
        //call the inventory controller to add the quantity
        InventoryDto savedInventoryDto = webClient.post()
                .uri("http://localhost:8082/api/inventory")
                .body(Mono.just(inventoryDto), InventoryDto.class)
                        .retrieve().bodyToMono(InventoryDto.class).block();

        log.info("Return back the saved product as product dto");
        if (savedInventoryDto != null) {
            log.info("Product and inventory record is saved successfully");
            return ProductMapper.productInventoryDtoToDto(savedProduct, savedInventoryDto);
        }
        else {
            throw new Exception("Fail in saving inventory");
        }
    }

    @Override
    public Long deleteProduct(String productCode) throws Exception {
        log.info("Delete the product by productCode");
        Long noOfDeletedProductRecord =  productRepository.deleteByProductCode(productCode);
        log.info("Delete the inventory by productCode");
        Long noOfDeletedInventoryRecord = webClient.delete()
                .uri("http://localhost:8082/api/inventory?productCode=" + productCode)
                .retrieve().bodyToMono(Long.class).block();

        if (noOfDeletedInventoryRecord != null) {
            if (noOfDeletedProductRecord.intValue() == noOfDeletedInventoryRecord.intValue()) {
                log.info("Product and inventory record is deleted successfully");
                return noOfDeletedProductRecord;
            }
            else {
                throw new Exception("Record deleted in product and inventory is not same");
            }
        }
        else {
            throw new Exception("Fail in deleting inventory");
        }
    }

    @Override
    public List<ProductDto> getAllProduct() {
        log.info("Get all product");
        List <Product> productList = productRepository.findAll();

        log.info("Get all inventory");
        Map<String, InventoryDto> inventoryDtoMap = webClient.get()
                .uri("http://localhost:8082/api/inventory")
                .retrieve().bodyToFlux(InventoryDto.class)
                .collectMap(InventoryDto::getProductCode, Function.identity()).block();

        if (inventoryDtoMap != null) {
            log.info("Create complete product dto list");
            List <ProductDto> productDtoList = new ArrayList<>();
            for (Product product: productList) {
                ProductDto productDto = ProductMapper.productToDto(product);
                InventoryDto inventoryDto = inventoryDtoMap.get(productDto.getProductCode());
                if (inventoryDto != null)
                    productDto.setQuantity(inventoryDto.getQuantity());
                productDtoList.add(productDto);
            }
            return productDtoList;
        }
        else {
            log.info("Cannot find inventory");
            return productList.stream().map(ProductMapper::productToDto).toList();
        }
    }

    @Override
    public void modifyPrice(String productCode, BigDecimal price) throws Exception {
        log.info("Get product by product code");
        Optional <Product> optionalProduct = productRepository.findByProductCode(productCode);
        if (optionalProduct.isPresent()) {
            log.info("Set the product price and save");
            Product product = optionalProduct.get();
            product.setPrice(price);
            productRepository.save(product);
        }
        else {
            throw new Exception("Invalid product code: Product not found");
        }
    }
}
