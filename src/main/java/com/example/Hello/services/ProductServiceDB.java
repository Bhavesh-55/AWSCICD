package com.example.Hello.services;


import com.example.Hello.Repos.CategoryRepo;
import com.example.Hello.Repos.ProductRepo;
import com.example.Hello.dtos.UserDto;
import com.example.Hello.models.Category;
import com.example.Hello.models.Product;
import com.example.Hello.models.State;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("DBSERVICE")
public class ProductServiceDB implements IProductService
{
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ObjectMapper objectMapper; // Used for updating only non-null fields

    //----------------------------------------------
    //service Discovery

    @Autowired
    private RestTemplate restTemplate;





    @Override
    public Product createProduct(Product product)
    {
        product.setCreatedAt(new Date());
        product.setState(State.INACTIVE);
        product.setLastUpdatedAt(new Date());
        return productRepo.save(product);
    }


    @Override
    public Product getProductById(long id)
    {
        Optional<Product> optionalProduct= productRepo.findById(id);
        if(optionalProduct.isPresent())
        {
            return optionalProduct.get();
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts()
    {
        return productRepo.findAll();
    }

    // Replace (update) a product by ID
    @Override
    public Product ReplaceProduct(Product product, long id)
    {

        Optional<Product> optionalProduct = productRepo.findById(id);

        if (optionalProduct.isPresent())
        {
            Product existingProduct = optionalProduct.get();

            existingProduct.setTitle(product.getTitle());
            existingProduct.setAmount(product.getAmount());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setImageurl(product.getImageurl());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setLastUpdatedAt(new Date());

            return productRepo.save(existingProduct);
        }
        return null;
    }

    @Override
    public Product updateProductState(long id)
    {

        // Check if the product with the given ID exists
        Optional<Product> optionalProduct = productRepo.findById(id);

        if (optionalProduct.isPresent())
        {
            Product product = optionalProduct.get();
            System.out.println("yes found");
            if (product.getState() == State.ACTIVE)
            {
                product.setState(State.INACTIVE);
                System.out.println("yes found & set inactive");

            } else {
                product.setState(State.ACTIVE);
            }
            product.setLastUpdatedAt(new Date());
            return productRepo.save(product);
        }
        else {
            return null;
        }
    }

    public Product modifyProduct(Product productUpdates, long id)
    {
        Optional<Product> optionalProduct = productRepo.findById(id);

        if (optionalProduct.isPresent())
        {
            Product existingProduct = optionalProduct.get();

            // Use ObjectMapper to merge updates without overriding null fields
            try
            {
                objectMapper.updateValue(existingProduct, productUpdates);
            }
            catch (JsonMappingException e)
            {
                throw new RuntimeException(e);
            }
            existingProduct.setLastUpdatedAt(new Date()); // Always update timestamp
            return productRepo.save(existingProduct);
        }
        return null; // Handle product not found case properly in the controller
    }



    @Override
    public Product DeleteProduct(long Id)
    {
        // Check if the product with the given ID exists
        Optional<Product> optionalProduct = productRepo.findById(Id);

        if (optionalProduct.isPresent())
        {
            Product productToDelete = optionalProduct.get();
            productRepo.deleteById(Id); // Delete the product from the database
            return productToDelete;
            // Return the deleted product as confirmation
        }
        return null; // Return null if the product does not exist
    }

    @Override
    public Category getCategoryById(long id)
    {
        Optional<Category> optionalCategory= categoryRepo.findById(id);
        if(optionalCategory.isPresent())
        {
            return optionalCategory.get();
        }
        return null;
    }



    @Override
    public Product getProductBasedOnUserRole(Long pid, Long userid)
    {
        Product product= productRepo.findById(pid).get();
        System.out.println("users ms called");
//        UserDto userDto = restTemplate.getForEntity("http://userService/users/{userid}", UserDto.class,userid).getBody();//imp
//       //http://localhost:9000/users/{userid}--direct without sd
//        if(userDto!=null){
//            return product;
//        }
        return null;

    }

}
