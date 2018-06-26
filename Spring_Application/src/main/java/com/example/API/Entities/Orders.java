package com.example.API.Entities;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private BigInteger id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
     private Date date;
    @PrePersist
    protected void onCreate() {
        date = new Date();
    }

    @PostPersist
    protected void onUpdate() {
        date = new Date();
    }


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Orders(){}
    public Orders(BigInteger id, Date date,BigInteger userId) {
        super();
        this.id = id;
        this.date = date;
        this.user=new User(userId," ",0000000000L," ");
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
