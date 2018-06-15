package com.example.API.Repository;

import com.example.API.Entities.Ingredients;
import org.springframework.data.repository.CrudRepository;
import java.math.BigInteger;
import java.util.List;

public interface IngredientRepository extends CrudRepository<Ingredients,BigInteger> {
    public List<Ingredients> findByProductId(BigInteger productId);
    public Ingredients findByIdAndProductId(BigInteger id,BigInteger productId);
    public List<Ingredients> findAllByProductId(BigInteger productId);

}
