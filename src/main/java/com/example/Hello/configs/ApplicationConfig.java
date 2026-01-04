package com.example.Hello.configs;


//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//3rd party call

@Configuration
public class ApplicationConfig {

    @Bean
//    @LoadBalanced
    public RestTemplate CreateRestTemplate()
    {
        return new RestTemplate();
    }
}
