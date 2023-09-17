package com.microservice.example.productservices.service.impl;

import com.microservice.example.productservices.dto.InventoryDto;
import com.microservice.example.productservices.dto.ProductDto;
import com.microservice.example.productservices.dto.response.AddProductDtoResponse;
import com.microservice.example.productservices.dto.response.AllProductDtoResponse;
import com.microservice.example.productservices.dto.response.DeleteProductDtoResponse;
import com.microservice.example.productservices.dto.response.ModifiedPriceResponse;
import com.microservice.example.productservices.entity.Product;
import com.microservice.example.productservices.repository.ProductRepository;
import com.microservice.example.productservices.service.ProductService;
import com.microservice.example.productservices.utils.ProductMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;

    @Override
    @CircuitBreaker(name = "${inventory.serviceName}", fallbackMethod = "addProductFallbackMethod")
    public AddProductDtoResponse addProduct(ProductDto productDto) {
        log.info("Convert the product dto to product entity object");
        Product product = ProductMapper.dtoToProduct(productDto);
        log.info("Create the Inventory dto");
        InventoryDto inventoryDto = ProductMapper.productDtoToInventoryDto(productDto);

        log.info("Saved the product entity");
        Product savedProduct = productRepository.save(product);

        log.info("Create span for saved inventory");
        Span nextSpan = tracer.nextSpan().name("savedInventorySpan");
        try (Tracer.SpanInScope spanInScope = tracer.withSpan(nextSpan.start())) {

            log.info("Call inventory controller to record the inventory of the new product");
            //call the inventory controller to add the quantity
            InventoryDto savedInventoryDto = webClientBuilder.build().post()
                    .uri("http://inventory-service/api/inventory")
                    .body(Mono.just(inventoryDto), InventoryDto.class)
                    .retrieve().bodyToMono(InventoryDto.class).block();

            log.info("Return back the saved product as product dto");
            if (savedInventoryDto != null) {
                log.info("Product and inventory record is saved successfully");
                ProductDto savedProductDto = ProductMapper.productInventoryDtoToDto(savedProduct, savedInventoryDto);
                return new AddProductDtoResponse(savedProductDto, null);
            } else {
                return new AddProductDtoResponse(null, "Fail in saving inventory");
            }
        } finally {
            nextSpan.end();
        }
    }

    @Override
    @CircuitBreaker(name = "${inventory.serviceName}", fallbackMethod = "deleteProductFallbackMethod")
    public DeleteProductDtoResponse deleteProduct(String productCode) {
        log.info("Delete the product by productCode");
        Long noOfDeletedProductRecord = productRepository.deleteByProductCode(productCode);

        Span nextSpan = tracer.nextSpan().name("deleteInventorySpan");
        try (Tracer.SpanInScope spanInScope = tracer.withSpan(nextSpan.start())) {
            log.info("Delete the inventory by productCode");
            Long noOfDeletedInventoryRecord = webClientBuilder.build().delete()
                    .uri("http://inventory-service/api/inventory?productCode=" + productCode)
                    .retrieve().bodyToMono(Long.class).block();

            if (noOfDeletedInventoryRecord != null) {
                if (noOfDeletedProductRecord.intValue() == noOfDeletedInventoryRecord.intValue()) {
                    log.info("Product and inventory record is deleted successfully");
                    return new DeleteProductDtoResponse(noOfDeletedProductRecord.intValue(), null);
                } else {
                    return new DeleteProductDtoResponse(0, "Record deleted in product and inventory is not same");
                }
            } else {
                return new DeleteProductDtoResponse(0, "Fail in deleting inventory");
            }
        } finally {
            nextSpan.end();
        }
    }

    @Override
    @CircuitBreaker(name = "${inventory.serviceName}", fallbackMethod = "getAllProductFallbackMethod")
    public AllProductDtoResponse getAllProduct() {
        log.info("Get all product");
        List<Product> productList = productRepository.findAll();

        log.info("Create get all product span");
        Span nextSpan = tracer.nextSpan().name("getAllInventorySpan");
        try (Tracer.SpanInScope spanInScope = tracer.withSpan(nextSpan.start())) {
            log.info("Get all inventory");
            Map<String, InventoryDto> inventoryDtoMap = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory")
                    .retrieve().bodyToFlux(InventoryDto.class)
                    .collectMap(InventoryDto::getProductCode, Function.identity()).block();

            if (inventoryDtoMap != null) {
                List<ProductDto> productDtoList = new ArrayList<>();
                for (Product product : productList) {
                    ProductDto productDto = ProductMapper.productToDto(product);
                    InventoryDto inventoryDto = inventoryDtoMap.get(productDto.getProductCode());
                    if (inventoryDto != null)
                        productDto.setQuantity(inventoryDto.getQuantity());
                    productDtoList.add(productDto);
                }
                return new AllProductDtoResponse(productDtoList, null);
            } else {
                log.info("Cannot find inventory");
                List<ProductDto> productDtoList = productList.stream().map(ProductMapper::productToDto).toList();
                return new AllProductDtoResponse(productDtoList, null);
            }
        } finally {
            nextSpan.end();
        }
    }

    @Override
    public ModifiedPriceResponse modifyPrice(String productCode, BigDecimal price) {
        log.info("Get product by product code");
        Optional<Product> optionalProduct = productRepository.findByProductCode(productCode);
        ModifiedPriceResponse modifiedPriceResponse = new ModifiedPriceResponse(true, null);
        if (optionalProduct.isPresent()) {
            log.info("Set the product price and save");
            Product product = optionalProduct.get();
            product.setPrice(price);
            productRepository.save(product);
        } else {
            modifiedPriceResponse.setPriceModified(false);
            modifiedPriceResponse.setErrorMessage("Invalid product code: Product not found");
        }
        return modifiedPriceResponse;
    }

    public AddProductDtoResponse addProductFallbackMethod(ProductDto productDto, Exception e) {
        log.info("Fail to add new product. Execute fall back method");
        return new AddProductDtoResponse(null, "Something is wrong with inventory");
    }

    public AllProductDtoResponse getAllProductFallbackMethod(Exception e) {
        log.info("Fail to get all product. Execute fall back method");
        return new AllProductDtoResponse(null, "Something is wrong with inventory");
    }

    public DeleteProductDtoResponse deleteProductFallbackMethod(String productCode, Exception e) {
        log.info("Fail to delete product: " + productCode + " Execute fall back method");
        return new DeleteProductDtoResponse(0, "Something is wrong with inventory");
    }
}
