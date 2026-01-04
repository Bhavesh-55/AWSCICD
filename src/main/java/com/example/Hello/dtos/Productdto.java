package com.example.Hello.dtos;

import com.example.Hello.models.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.antlr.v4.runtime.misc.NotNull;


public class Productdto
{
    private long id;
    @NotNull
    private String title;
    private String description;
    private String imageurl;
    @NotNull
    private Double amount;
    private Categorydto categorydto;

//getter and setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Categorydto getCategorydto() {
        return categorydto;
    }

    public void setCategorydto(Categorydto categorydto) {
        this.categorydto = categorydto;
    }
}
