package com.example.API.Repository;

import com.example.API.Entities.Product;
import org.springframework.data.repository.CrudRepository;
import java.math.BigInteger;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product,BigInteger> {
    public List<Product> findByOrdersId(BigInteger orderId);
    public Product findByIdAndOrdersId(BigInteger id,BigInteger orderId);
    public List<Product>findAllByOrdersId(BigInteger orderId);


}

