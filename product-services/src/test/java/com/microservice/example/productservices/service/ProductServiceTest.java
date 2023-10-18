package com.microservice.example.productservices.service;

import com.microservice.example.productservices.dto.InventoryDto;
import com.microservice.example.productservices.dto.ProductDto;
import com.microservice.example.productservices.dto.response.AddProductDtoResponse;
import com.microservice.example.productservices.dto.response.AllProductDtoResponse;
import com.microservice.example.productservices.dto.response.DeleteProductDtoResponse;
import com.microservice.example.productservices.dto.response.ModifiedPriceResponse;
import com.microservice.example.productservices.entity.Product;
import com.microservice.example.productservices.repository.ProductRepository;
import com.microservice.example.productservices.service.impl.ProductServiceImpl;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private Tracer tracer;
    @Mock
    private WebClient.Builder webClientBuilder;
    @Mock
    private WebClient webClient;
    @Mock
    private Span span;
    @InjectMocks
    private ProductServiceImpl productService;


    @Test
    public void addProductTest() {
        ProductDto productDto = createProductDto();

        when(productRepository.save(any())).then(returnsFirstArg());
        when(tracer.nextSpan()).thenReturn(span);
        when(tracer.nextSpan().name(any())).thenReturn(span);

        when(webClientBuilder.build()).thenReturn(webClient);
        RequestBodyUriSpec requestBodyUriSpec = mock(RequestBodyUriSpec.class);
        when(webClient.post()).thenReturn(requestBodyUriSpec);

        RequestBodySpec requestBodySpec = mock(RequestBodySpec.class);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);

        RequestHeadersSpec requestHeadersSpec = mock(RequestHeadersSpec.class);
        when(requestBodySpec.body(any(Mono.class), Mockito.any(Class.class))).thenReturn(requestHeadersSpec);

        ResponseSpec responseSpec = mock(ResponseSpec.class);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(InventoryDto.class)).thenReturn(Mono.just(new InventoryDto()));

        AddProductDtoResponse addProductDtoResponse = productService.addProduct(productDto);
        Assertions.assertNull(addProductDtoResponse.getErrorMessage());
        Assertions.assertEquals(productDto.getProductCode(), addProductDtoResponse.getProductDto().getProductCode());
    }

    @Test
    public void deleteProductTest() {
        when(productRepository.deleteByProductCode(anyString())).thenReturn(1L);

        when(tracer.nextSpan()).thenReturn(span);
        when(tracer.nextSpan().name(any())).thenReturn(span);

        when(webClientBuilder.build()).thenReturn(webClient);

        RequestHeadersUriSpec requestHeadersUriSpec = mock(RequestHeadersUriSpec.class);
        when(webClient.delete()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);

        ResponseSpec responseSpec = mock(ResponseSpec.class);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Long.class)).thenReturn(Mono.just(1L));

        DeleteProductDtoResponse deleteProductDtoResponse = productService.deleteProduct(anyString());
        Assertions.assertEquals(1, deleteProductDtoResponse.getNoOfRecordDeleted());
        Assertions.assertNull(deleteProductDtoResponse.getErrorMessage());
    }

    @Test
    public void getAllProductTest() {
        when(productRepository.findAll()).thenReturn(createProductList());

        when(tracer.nextSpan()).thenReturn(span);
        when(tracer.nextSpan().name(any())).thenReturn(span);

        when(webClientBuilder.build()).thenReturn(webClient);

        RequestHeadersUriSpec requestHeadersUriSpec = mock(RequestHeadersUriSpec.class);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);

        ResponseSpec responseSpec = mock(ResponseSpec.class);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.bodyToFlux(InventoryDto.class)).thenReturn(Flux.just(new InventoryDto()));

        AllProductDtoResponse allProductDtoResponse = productService.getAllProduct();
        Assertions.assertEquals(2, allProductDtoResponse.getProductDtoList().size());
        Assertions.assertNull(allProductDtoResponse.getErrorMessage());
    }

    @Test
    public void modifyPriceTestPositive() {
        when(productRepository.findByProductCode(anyString())).thenReturn(Optional.of(createProduct()));
        ModifiedPriceResponse modifiedPriceResponse = productService.modifyPrice(anyString(), new BigDecimal(1330));
        Assertions.assertTrue(modifiedPriceResponse.isPriceModified());
        Assertions.assertNull(modifiedPriceResponse.getErrorMessage());
    }

    private static ProductDto createProductDto() {
        ProductDto productDto = new ProductDto();

        productDto.setProductCode("IPHONE-13");
        productDto.setName("Iphone 13");
        productDto.setDescription("Latest Iphone 13");
        productDto.setPrice(new BigDecimal(1300));
        productDto.setQuantity(130);
        return productDto;
    }

    private static Product createProduct() {
        Product product = new Product();

        product.setProductCode("IPHONE-13");
        product.setName("Iphone 13");
        product.setDescription("Latest Iphone 13");
        product.setPrice(new BigDecimal(1300));
        return product;
    }

    private static List<Product> createProductList() {
        List <Product> productList = new ArrayList<>();

        Product productOne = new Product();
        productOne.setProductCode("IPHONE-13");
        productOne.setName("Iphone 13");
        productOne.setDescription("Latest Iphone 13");
        productOne.setPrice(new BigDecimal(1300));
        productList.add(productOne);

        Product productTwo = new Product();
        productTwo.setProductCode("IPHONE-14");
        productTwo.setName("Iphone 14");
        productTwo.setDescription("Latest Iphone 14");
        productTwo.setPrice(new BigDecimal(1400));
        productList.add(productTwo);
        return productList;
    }

    private static Map<String, InventoryDto> createInventoryDtoMap(){
        Map<String, InventoryDto> inventoryDtoMap = new HashMap<>();
        InventoryDto inventoryDtoOne = new InventoryDto();
        inventoryDtoOne.setProductCode("IPHONE-13");
        inventoryDtoOne.setQuantity(130);
        inventoryDtoMap.put("IPHONE-13", inventoryDtoOne);

        InventoryDto inventoryDtoTwo = new InventoryDto();
        inventoryDtoTwo.setProductCode("IPHONE-14");
        inventoryDtoTwo.setQuantity(140);
        inventoryDtoMap.put("IPHONE-14", inventoryDtoTwo);
        return inventoryDtoMap;
    }

    //    @Test
//    public void addProductTest_UsingMockWebServer() throws Exception {
//        ProductDto productDto = createProductDto();
//
//        when(productRepository.save(any())).then(returnsFirstArg());
//        when(tracer.nextSpan()).thenReturn(span);
//        when(tracer.nextSpan().name(any())).thenReturn(span);
//
//        MockWebServer mockWebServer = new MockWebServer();
//        mockWebServer.start();
//
//        WebClient.Builder webClientBuilder = WebClient.builder().baseUrl("http://127.0.0.1:" + mockWebServer.getPort());
//
//        // Use the mock server for the web client
//        ReflectionTestUtils.setField(productService, "webClientBuilder", webClientBuilder);
//        // Define the expected response from the mock server
//        mockWebServer.enqueue(new MockResponse()
//                .setResponseCode(200)
//                .setHeader("Content-Type", "application/json"));
//
//        AddProductDtoResponse addProductDtoResponse = productService.addProduct(productDto);
//        Assertions.assertNull(addProductDtoResponse.getErrorMessage());
//        Assertions.assertEquals(productDto.getProductCode(), addProductDtoResponse.getProductDto().getProductCode());
//        // Shutdown the mock server
//        mockWebServer.shutdown();
//    }
}
