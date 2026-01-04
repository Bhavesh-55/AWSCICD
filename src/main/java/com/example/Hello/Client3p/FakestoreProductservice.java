package com.example.Hello.Client3p;

import com.example.Hello.dtos.FakestoreProductdto;
import com.example.Hello.models.Category;
import com.example.Hello.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class FakestoreProductservice
{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public FakestoreProductservice(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

 //API


    public Product createProduct(Product product)
    {
        FakestoreProductdto fakestoreProductdto=from(product);
        FakestoreProductdto fProductdto=RequestForEntity("https://fakestoreapi.com/products",HttpMethod.POST,fakestoreProductdto,FakestoreProductdto.class).getBody();
        return from(fProductdto);
    }



    public List<Product> getAllProducts() {
        FakestoreProductdto[] fakestoreProductdtos;
        fakestoreProductdtos=restTemplate.getForEntity("https://fakestoreapi.com/products",FakestoreProductdto[].class).getBody(); //here need to pass array not arraylist

        List<Product> productsList=new ArrayList<>();
        for(FakestoreProductdto fakestoreProductdto:fakestoreProductdtos){
            productsList.add(from(fakestoreProductdto));
        }
        return productsList;
    }


    public Product getProductById(long id)
    {
        //check in cache
            //if found return else store

        FakestoreProductdto fakestoreProductdto = (FakestoreProductdto) redisTemplate.opsForHash().get("products",id);
        if(fakestoreProductdto!=null)
        {
            System.out.println("Found in cache");
            return from(fakestoreProductdto);
        }
        else
        {
            System.out.println("Not found in cache");

            ResponseEntity<FakestoreProductdto> fakestoreProductdtoResponseEntity;
            fakestoreProductdtoResponseEntity= restTemplate.getForEntity("https://fakestoreapi.com/products/{myid}",FakestoreProductdto.class,id);
            fakestoreProductdto=fakestoreProductdtoResponseEntity.getBody();

            if(fakestoreProductdtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) && fakestoreProductdto !=null)//IMP CHECK BOTH 200 && !null
            {
                // Store the product in Redis cache and return it
                redisTemplate.opsForHash().put("products", id, fakestoreProductdto);
                return from(fakestoreProductdto);
            } else {
                return null;
            }
        }
    }


    private  <T> ResponseEntity<T> RequestForEntity(String url, HttpMethod method, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException
    {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url,method, requestCallback, responseExtractor, uriVariables);
    }




    public Product ReplaceProduct(Product product,long Id)
    {
        FakestoreProductdto fakestoreProductdto=from(product);
        FakestoreProductdto fProductdto=RequestForEntity("https://fakestoreapi.com/products/{Id}",HttpMethod.PUT,fakestoreProductdto,FakestoreProductdto.class,Id).getBody();
        return from(fProductdto);
    }


    public Product DeleteProduct(long Id) {
        FakestoreProductdto fakestoreProductdto;
        fakestoreProductdto=RequestForEntity("https://fakestoreapi.com/products/{Id}",HttpMethod.DELETE,null,FakestoreProductdto.class,Id).getBody();
        return from(fakestoreProductdto);
    }








    private Product from(FakestoreProductdto fakestoreProductdto)
    {
        Product product = new Product();
        product.setId(fakestoreProductdto.getId());
        product.setAmount(fakestoreProductdto.getPrice());
        product.setTitle(fakestoreProductdto.getTitle());
        product.setDescription(fakestoreProductdto.getDescription());
        product.setImageurl(fakestoreProductdto.getImage());
        Category category= new Category();
        category.setName(fakestoreProductdto.getCategory());
        product.setCategory(category);
        return product;

    }
    private FakestoreProductdto from (Product product) {
        FakestoreProductdto pDto = new FakestoreProductdto();
        pDto.setId(product.getId());
        pDto.setTitle(product.getTitle());
        pDto.setImage(product.getImageurl());
        pDto.setPrice(product.getAmount());
        pDto.setDescription(product.getDescription());
        if (product.getCategory() != null) {
            pDto.setCategory(product.getCategory().getName());
        }
        return pDto;
    }




}
