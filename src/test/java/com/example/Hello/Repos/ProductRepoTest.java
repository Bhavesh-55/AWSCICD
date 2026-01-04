package com.example.Hello.Repos;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.Hello.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProductRepoTest {

    @Autowired
    private ProductRepo productRepo;


    @Test
    public void testQueries() {
        List<Product> productList = productRepo.findProductByAmountBetween(10D,2000D);
            System.out.println("output: ");
        for(Product product : productList) {

            System.out.println(product.getTitle()+" -->"+product.getAmount());
        }
    }

}
