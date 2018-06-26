package com.example.API.Controller;

import com.example.API.Entities.Ingredients;
import com.example.API.Entities.Product;
import com.example.API.Services.IngredientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;


@RestController
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @ApiOperation(value="Return all Ingredients with its details")
    @RequestMapping("/ingredients")
    public Iterable<Ingredients> getEveryIngredient(){
        return ingredientService.getEveryIngredient();
    }

    @ApiOperation(value="Return Ingredient by Id")
    @RequestMapping("/ingredients/{ingredientId}")
    public Ingredients getIngredientById(@PathVariable BigInteger ingredientId) {
        return ingredientService.getIngredientbyId(ingredientId);
    }

    @ApiOperation(value="Return all Ingredients of a particular Product")
    @RequestMapping("/products/{productId}/ingredients")
    public List<Ingredients>getAllIngredientsById(@PathVariable BigInteger productId) {
        return ingredientService.getAllIngredientsById(productId);
    }

    @ApiOperation(value="Return all Ingredients of a particular product in a particular order made by a user")
    @RequestMapping("/users/{userId}/orders/{orderId}/products/{id}/ingredients")
    public List<Ingredients> getAllIngredients(@PathVariable BigInteger id) {
        return ingredientService.getAllIngredients(id);
    }

    @ApiOperation(value="Return a particular Ingredient of a particular product in a particular order made by a user")
    @RequestMapping("/users/{userId}/orders/{orderId}/products/{productId}/ingredients/{id}")
    public Ingredients getIngredients(@PathVariable BigInteger id,@PathVariable BigInteger productId){
        return ingredientService.getIngredients(productId,id);
    }

    @ApiOperation(value="Adds a new ingredient to a product")
    @RequestMapping(method = RequestMethod.POST,value = "/products/{productId}/ingredient")
    public Ingredients addIngredients(@RequestBody Ingredients ingredients,@PathVariable BigInteger productId){
        ingredients.setProduct(new Product(productId,0.0));
        return ingredientService.addIngredients(ingredients);
    }

    @ApiOperation(value="Adds a List of new Ingredients")
    @RequestMapping(method = RequestMethod.POST,value = "/products/{productId}/ingredients")
    public List<Ingredients> addIngredients(@RequestBody List<Ingredients> ingredients,@PathVariable BigInteger productId){
        for (int i=0;i<ingredients.size();i++)
            ingredients.get(i).setProduct(new Product(productId,0.0));
        return ingredientService.addIngredients(ingredients);
    }

    @ApiOperation(value="Update an Ingredient")
    @RequestMapping(method = RequestMethod.PUT, value="/products/{productId}/ingredients/{id}")
    public Ingredients updateIngredients(@RequestBody Ingredients ingredients,@PathVariable BigInteger id,@PathVariable BigInteger productId)throws Exception {
        ingredients.setProduct(new Product(productId,0.0));
        return ingredientService.updateIngredients(ingredients, id,productId);
    }

    @ApiOperation(value="Delete an Ingredient")
    @RequestMapping(method = RequestMethod.DELETE, value="/products/{productId}/ingredients/{id}")
    public void deleteIngredients(@PathVariable BigInteger id){
        ingredientService.deleteIngredients(id);
    }
}
