package com.example.Hello.services;

import com.example.Hello.models.Category;
import com.example.Hello.models.Product;

import java.util.List;

public interface IProductService
{
    List<Product> getAllProducts();
    Product getProductById(long id);
    Product createProduct(Product product);
    Product ReplaceProduct(Product product,long Id);
    Product updateProductState(long id);
    Product modifyProduct(Product product,long id);
    Product DeleteProduct(long Id);
    Category getCategoryById(long id);
    Product getProductBasedOnUserRole(Long pid,Long userid);
}
