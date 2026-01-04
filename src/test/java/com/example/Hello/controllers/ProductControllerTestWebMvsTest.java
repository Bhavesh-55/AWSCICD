package com.example.Hello.controllers;

import com.example.Hello.dtos.Productdto;
import com.example.Hello.models.Product;
import com.example.Hello.services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTestWebMvsTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void TestGetAllProducts_RunSuccessfully() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("MacBookPro");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setTitle("Iphone");

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product2);

        String outputInString = objectMapper.writeValueAsString(productList);

        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/Products"))
                .andExpect(status().isOk())
                .andExpect(content().string(outputInString))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[0].title").value("MacBookPro"));
    }

    @Test
    public void Test_CreateProduct_RunSuccessfully() throws Exception {
        //Arrange
        Product product = new Product();
        product.setTitle("Notebook");
        product.setId(101L);

        Productdto productDto = new Productdto();
        productDto.setId(101L);
        productDto.setTitle("Notebook");

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        //Act
        mockMvc.perform(post("/Products")
                        .contentType(MediaType.APPLICATION_JSON) //content() --request body
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDto))) //content()--res body
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.title").value("Notebook"));
    }


//{
//    "id" : 101, "title" : "Notebook"  $.id , $.title
//}


}