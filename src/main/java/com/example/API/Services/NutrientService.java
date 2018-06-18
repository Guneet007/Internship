package com.example.API.Services;

import com.example.API.Entities.Nutrients;
import com.example.API.Exceptions.NutrientException;
import com.example.API.Repository.NutrientRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NutrientService {
    @Autowired
    private NutrientRepository nutrientRepository;

    public Iterable<Nutrients>getEveryNutrient(){
        return nutrientRepository.findAll();
    }

    public Nutrients getNutrientById(BigInteger id) {
        Optional<Nutrients>nutrients= nutrientRepository.findById(id);
        if(!nutrients.isPresent())
            throw new NutrientException("No nutrient found with id ="+id);
        return nutrients.get();
    }

    public List<Nutrients>getAllNutrientsByIngredients(BigInteger ingredientId){
        if(nutrientRepository.findAllByIngredientsId(ingredientId).isEmpty())
            throw new NutrientException("No nutrient found for ingredient id="+ingredientId+" or the ingredient does not exist");
        return nutrientRepository.findAllByIngredientsId(ingredientId);
    }

    public Nutrients getNutrientByIngredient(BigInteger ingredientId,BigInteger nutrientId) {
        return nutrientRepository.findByIdAndIngredientsId(nutrientId,ingredientId);
    }

    public List<Nutrients>getAllNutrientsOfProduct(BigInteger ingredientId,BigInteger productId){
        if(nutrientRepository.findAllByProductId(productId).isEmpty())
            throw new NutrientException("No nutrient found for product id="+productId);
        return nutrientRepository.findAllByProductId(productId);
    }

    public List<Nutrients> getAllNutrients(BigInteger id){
        List<Nutrients> nutrients=new ArrayList<>();
        nutrientRepository.findByIngredientsId(id)
                .forEach(nutrients::add);
        if(nutrients.isEmpty())
            throw new NutrientException("No nutrient found");
        return nutrients;
    }

    public Nutrients addNutrient(Nutrients nutrients) {
        return nutrientRepository.save(nutrients);
    }

    public List<Nutrients> addNutrient(List<Nutrients> nutrients){
        for (int i=0;i<nutrients.size();i++)
            nutrientRepository.save(nutrients.get(i));
        return nutrients;
    }

    public Nutrients getNutrient(BigInteger ingredientId,BigInteger id) {
        return nutrientRepository.findByIdAndIngredientsId(id,ingredientId);
    }

    public Nutrients updateNutrient(Nutrients nutrients, BigInteger id,BigInteger ingredientId)throws Exception {
        Nutrients nutrientsFromDB = getNutrient(ingredientId,id);
        if(nutrients !=null) {
            BeanUtils.copyProperties(nutrients,nutrientsFromDB);
            nutrientsFromDB.setId(id);
        } else {
            throw new NotFoundException("not found");
        }
        return nutrientRepository.save(nutrientsFromDB);
    }


    public void deleteNutrient (BigInteger id){
        nutrientRepository.deleteById(id);
    }
}
