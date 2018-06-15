package com.example.API.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;
import java.util.Set;

@Entity
public class Ingredients {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private BigInteger id;
    @NotNull
    @Pattern(regexp="^[A-Za-z]*[A-Za-z-'. ]*[A-Za-z]*$",message = "Name has invalid characters")
    private String name;

    @ManyToOne
    private Product product;

    public Ingredients(){}
    public Ingredients(BigInteger id, String name,BigInteger productId,BigInteger orderId,BigInteger userid) {
        super();
        this.id = id;
        this.name = name;
        this.product=new Product(productId,0.0,orderId,userid);

    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
