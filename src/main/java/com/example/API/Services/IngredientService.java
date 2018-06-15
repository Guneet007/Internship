package com.example.API.Services;

import com.example.API.Entities.Ingredients;
import com.example.API.Repository.IngredientRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public Iterable<Ingredients>getEveryIngredient() {
        return ingredientRepository.findAll();
    }

    public  Ingredients getIngredientbyId(BigInteger id){
        Optional<Ingredients> ingredients= ingredientRepository.findById(id);
        return ingredients.get();
    }

    public List<Ingredients>getAllIngredientsById(BigInteger id) {
        return ingredientRepository.findAllByProductId(id);
    }

    public List<Ingredients> getAllIngredients(BigInteger id){
        List<Ingredients> ingredients=new ArrayList<>();
        ingredientRepository.findByProductId(id)
                .forEach(ingredients::add);
        return ingredients;
    }

    public Ingredients addIngredients(Ingredients ingredients) {
        return ingredientRepository.save(ingredients);
    }

    public List<Ingredients>addIngredients(List<Ingredients> ingredients){
        for (int i=0;i<ingredients.size();i++)
            ingredientRepository.save(ingredients.get(i));
        return ingredients;
    }

    public Ingredients getIngredients(BigInteger productId,BigInteger id) {
        return ingredientRepository.findByIdAndProductId(id,productId);
    }

    public Ingredients updateIngredients(Ingredients ingredients, BigInteger id,BigInteger productId)throws Exception {
        Ingredients ingredientFromDB = getIngredients(productId,id);
        if(ingredients !=null) {
            BeanUtils.copyProperties(ingredients,ingredientFromDB);
            ingredientFromDB.setId(id);
        } else {
            throw new NotFoundException("not found");
        }
        return ingredientRepository.save(ingredientFromDB);
    }

    public void deleteIngredients(BigInteger id) {
        ingredientRepository.deleteById(id);
    }
}
