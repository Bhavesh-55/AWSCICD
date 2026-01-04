package com.example.Hello.Repos;

import com.example.Hello.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long>
{

   List<Product> findProductByAmountBetween(double low, double high);


}
//It will provide CRUD operations (Create, Read, Update, Delete) for the Product entity.
//Spring Data JPA will automatically generate the implementation for common CRUD (Create, Read, Update, Delete) operations.