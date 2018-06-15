package com.example.API.Repository;

import com.example.API.Entities.Nutrients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface NutrientRepository extends JpaRepository<Nutrients,BigInteger> {

    public List<Nutrients> findByIngredientsId(BigInteger ingredientId);
    public Nutrients findByIdAndIngredientsId(BigInteger id,BigInteger ingredientId);
    public List<Nutrients> findAllByIngredientsId(BigInteger ingredientId);
    public List<Nutrients> findAllByProductId(BigInteger productId);

//    @Query(value = "SELECT nutrients from nutrients,ingredients,product where nutrients.ingredients_id=ingredients.id and ingredients.product_id=product.id and product.id=(:productId)",nativeQuery = true)
//    public List<Nutrients>findAllByProductId(@Param("productId")BigInteger productId);

}
