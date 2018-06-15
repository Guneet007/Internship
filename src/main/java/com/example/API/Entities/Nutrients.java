package com.example.API.Entities;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;

@Entity
public class Nutrients {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private BigInteger id;
    @NotNull
    @Pattern(regexp="^[A-Za-z]*[A-Za-z-'. ]*[A-Za-z]*$",message = "Name has invalid characters")
    private String name;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @NotNull
    @Range(min = 0,max = 1000)
    private double calories;

    @ManyToOne
    private Ingredients ingredients;
    @ManyToOne
    private Product product;


    public Nutrients(){}



    public Nutrients(BigInteger id, String name, double calories, BigInteger ingredientId, BigInteger productId, BigInteger orderId, BigInteger userId) {

        super();
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.ingredients=new Ingredients(ingredientId," ",productId,orderId,userId);
        this.product=new Product(productId,0.0,orderId,userId);
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

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients ingredients) {
        this.ingredients = ingredients;
    }

}
