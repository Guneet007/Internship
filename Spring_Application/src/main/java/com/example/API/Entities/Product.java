package com.example.API.Entities;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Date;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private BigInteger id;

    @NotNull
    @Range(min=0)
    private double price;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;

    public Product(){}
    public Product(BigInteger productId,double price){
        super();
        this.id = productId;
        this.price = price;
    }
    public Product(BigInteger id, double price,BigInteger orderId,BigInteger userId) {
        super();
        this.id = id;
        this.price = price;
        Date date = new Date();
        this.orders=new Orders(orderId,date,userId);

    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
  }

   // public Orders getOrders() {
     //   return orders;
    //}

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

}
