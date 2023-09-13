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

import java.util.List;
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
        if (savedInventoryDto != null)
            return ProductMapper.productInventoryDtoToDto(savedProduct, savedInventoryDto);
        else
            throw new Exception("Fail in saving inventory");
    }

    @Override
    public Long deleteProduct(String productCode) {
        log.info("Delete the product by productCode");
        return productRepository.deleteByProductCode(productCode);
    }

    @Override
    public List<ProductDto> getAllProduct() {
        List <Product> productList = productRepository.findAll();
        return productList.stream().map(ProductMapper::productToDto).toList();
    }
}
