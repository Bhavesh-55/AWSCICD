package com.example.Hello.controllers;

import com.example.Hello.dtos.Productdto;
import com.example.Hello.models.Product;
import com.example.Hello.services.IProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

        @Autowired
        private ProductController productController;

        @MockBean
        private IProductService productService;

        @Captor
        private ArgumentCaptor<Long> idCaptor;

        @Test
        public void TestGetProductById_WithValidProductId_RunSuccessfully() {
            //Arrange
            Long productId = 2L;
            Product product = new Product();
            product.setId(productId);
            product.setTitle("Iphone16");
            when(productService.getProductById(productId))
                    .thenReturn(product);


            //Act
            ResponseEntity<Productdto> response =
                    productController.getProductById(productId);

            //Assert
            assertNotNull(response);
            assertNotNull(response.getBody());
            assertEquals(productId, response.getBody().getId());
            assertEquals("Iphone16", response.getBody().getTitle());
            assertEquals(200, response.getStatusCode().value());
        }

        @Test
        @DisplayName("passing id -1 lead to product not found")
        public void TestGetProductById_WithInvalidId_ResultsInRuntimeException() {
            //Act and Assert
            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> productController.getProductById(-1L));

            assertEquals("Product not found", exception.getMessage());
        }

        @Test
        public void TestGetProductById_WhenProductServiceThrowsException() {
            //Assert
            when(productService.getProductById(any(Long.class)))
                    .thenThrow(new RuntimeException("kuch toh phata hai !!"));

            assertThrows(RuntimeException.class,
                    () -> productController.getProductById(1000L));
        }

        @Test
        public void Test_GetProductById_ServiceCalledWithExpectedArguments_RunSuccessfully() {
            //Arrange
            Long productId = 1L;
            Product product = new Product();
            product.setId(productId);
            product.setTitle("Nokia");
            when(productService.getProductById(any(Long.class)))
                    .thenReturn(product);
            //Act
            productController.getProductById(productId);

            //Assert //verify is used for mock bean
            
            verify(productService).getProductById(idCaptor.capture());
            assertEquals(productId,idCaptor.getValue());
        }
    }