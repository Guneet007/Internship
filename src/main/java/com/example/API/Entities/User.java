package com.example.API.Entities;

import org.hibernate.validator.constraints.Range;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private BigInteger id;

    @NotNull
    @Pattern(regexp="^[A-Za-z]*[A-Za-z-'. ]*[A-Za-z]*$",message = "Name has invalid characters")
    private String username;

    @NotNull
    @Range(min = 6400000000L ,max=9999999999L)
    private Long phoneNumber;

    @NotNull
    private String address;

    public User(){}

    public User(BigInteger id, String username, Long phoneNumber, String address) {
        super();
        this.id = id;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;

    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
