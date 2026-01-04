package com.example.Hello.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.Date;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseModel
{
    @Id
    private long Id;

    @Column(columnDefinition = "DATETIME(0)")
    private Date CreatedAt;

    @Column(columnDefinition = "DATETIME(0)")
    private Date lastUpdatedAt;

    @Enumerated(EnumType.STRING)
    private State state;


    //getter and setter

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
