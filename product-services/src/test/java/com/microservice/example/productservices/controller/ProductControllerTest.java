package com.microservice.example.productservices.controller;

import com.microservice.example.productservices.dto.ProductDto;
import com.microservice.example.productservices.dto.response.AddProductDtoResponse;
import com.microservice.example.productservices.dto.response.AllProductDtoResponse;
import com.microservice.example.productservices.dto.response.DeleteProductDtoResponse;
import com.microservice.example.productservices.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(ProductController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addProductTest() throws Exception {
        Mockito.when(productService.addProduct(Mockito.any())).thenReturn(createAddProductDtoResponse());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProductDtoJson()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteProductTest() throws Exception {
        Mockito.when(productService.deleteProduct(Mockito.anyString())).thenReturn(createDeleteProductDtoResponse());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/product?productCode={productCodeStr}","IPHONE-13"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllProductTest() throws Exception {
        Mockito.when(productService.getAllProduct()).thenReturn(createAllProductDtoResponse());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    private static AddProductDtoResponse createAddProductDtoResponse() {
        ProductDto productDto = new ProductDto();
        productDto.setProductCode("IPHONE-13");
        productDto.setName("Iphone 13");
        productDto.setDescription("Latest Iphone 13");
        productDto.setPrice(new BigDecimal(1300));
        productDto.setQuantity(130);

        AddProductDtoResponse addProductDtoResponse = new AddProductDtoResponse();
        addProductDtoResponse.setProductDto(productDto);
        return addProductDtoResponse;
    }

    private static DeleteProductDtoResponse createDeleteProductDtoResponse() {
        DeleteProductDtoResponse deleteProductDtoResponse = new DeleteProductDtoResponse();
        deleteProductDtoResponse.setNoOfRecordDeleted(1);
        return deleteProductDtoResponse;
    }

    private static AllProductDtoResponse createAllProductDtoResponse() {
        ProductDto productDto = new ProductDto();
        productDto.setProductCode("IPHONE-13");
        productDto.setName("Iphone 13");
        productDto.setDescription("Latest Iphone 13");
        productDto.setPrice(new BigDecimal(1300));
        productDto.setQuantity(130);

        AllProductDtoResponse allProductDtoResponse = new AllProductDtoResponse();
        allProductDtoResponse.setProductDtoList(List.of(productDto));
        return allProductDtoResponse;
    }

    private static String createProductDtoJson() {
        StringBuilder builder = new StringBuilder("{\"productCode\":\"IPHONE-13\",");
        builder.append("\"name\":\"IPhone 17\",");
        builder.append("\"description\":\"Latest IPhone 17\",");
        builder.append("\"price\":1700,");
        builder.append("\"quantity\":170}");
        return builder.toString();
    }
}
