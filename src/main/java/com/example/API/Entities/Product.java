package com.example.API.Entities;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.security.Timestamp;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static java.lang.System.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private BigInteger id;

    @NotNull(message = "Price is compulsary")
    @Range(min=0)
    private double price;

    @ManyToOne
    private Orders orders;

    public Product(){}
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

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

}
