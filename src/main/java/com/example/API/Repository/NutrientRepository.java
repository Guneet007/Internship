package com.example.API.Repository;

import com.example.API.Entities.Nutrients;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigInteger;
import java.util.List;

public interface NutrientRepository extends JpaRepository<Nutrients,BigInteger> {

    public List<Nutrients> findByIngredientsId(BigInteger ingredientId);
    public Nutrients findByIdAndIngredientsId(BigInteger id,BigInteger ingredientId);
    public List<Nutrients> findAllByIngredientsId(BigInteger ingredientId);
    public List<Nutrients> findAllByProductId(BigInteger productId);


}
