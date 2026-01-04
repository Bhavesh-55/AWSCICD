package com.example.Hello.services;
import com.example.Hello.Client3p.FakestoreProductservice;
import com.example.Hello.models.Category;
import com.example.Hello.models.Product;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("3pSERVICE")
//@Primary
public class ProductService3p implements IProductService
{
    private FakestoreProductservice fakestoreProductservice;

    public ProductService3p(FakestoreProductservice fakestoreProductservice)
    {
        this.fakestoreProductservice = fakestoreProductservice;
    }

    @Override
    public Product createProduct(Product product) {
        return fakestoreProductservice.createProduct(product);
    }


    @Override
    public List<Product> getAllProducts(){
        return fakestoreProductservice.getAllProducts();
    }

    @Override
    public Product getProductById(long id){
        return fakestoreProductservice.getProductById(id);
    }



    @Override
    public Product ReplaceProduct(Product product,long Id){
        return fakestoreProductservice.ReplaceProduct(product,Id);
    }

    @Override
    public Product updateProductState(long id) {
        return null;//It is used for db
    }

    @Override
    public Product modifyProduct(Product product, long id) {
        return null;
    }

    @Override
    public Product DeleteProduct(long Id) {
        return fakestoreProductservice.DeleteProduct(Id);
    }

    @Override
    public Category getCategoryById(long id) {
        return null;
    }

    @Override
    public Product getProductBasedOnUserRole(Long pid, Long userid) {
        return null;
    }


}



