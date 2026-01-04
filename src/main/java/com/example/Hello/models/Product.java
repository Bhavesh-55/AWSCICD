package com.example.Hello.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

//In Java, field names should always start with a lowercase letter (imageUrl).
//Spring Boot and Jackson are case-sensitive, which is why imageurl in your request isn't mapping to imageUrl in your entity.
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) //only not null field will be sent
@Entity
public class Product extends BaseModel
{
    private String title;
    private String description;
    @JsonProperty("imageurl")
    private String imageurl;
    private Double amount;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn
    @JsonIgnore
    private Category category;
    private boolean IsPrimespecific;


    public boolean isPrimespecific() {
        return IsPrimespecific;
    }

    public void setPrimespecific(boolean primespecific) {
        IsPrimespecific = primespecific;
    }
}
