package com.example.API.Repository;

import com.example.API.Entities.Orders;
import org.springframework.data.repository.CrudRepository;
import java.math.BigInteger;
import java.util.List;

public interface OrdersRepository extends CrudRepository<Orders,BigInteger> {
    public List<Orders>findByUserId(BigInteger userId);
}
