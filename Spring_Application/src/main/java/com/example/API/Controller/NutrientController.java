package com.example.API.Controller;

import com.example.API.Entities.Ingredients;
import com.example.API.Entities.Nutrients;
import com.example.API.Entities.Product;
import com.example.API.Services.NutrientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;

@RequestMapping("/rest")
@RestController
public class NutrientController {
    @Autowired
    private NutrientService nutrientService;

    @ApiOperation(value="Return all Nutrients")
    @RequestMapping("/nutrients")
    public  Iterable<Nutrients>getEveryNutrient(){
        return  nutrientService.getEveryNutrient();
    }

    @ApiOperation(value="Return a particular Nutrient")
    @RequestMapping("/nutrients/{nutrientId}")
    public Nutrients getNutrientById(@PathVariable BigInteger nutrientId){
        return nutrientService.getNutrientById(nutrientId);
    }

    @ApiOperation(value="Return all Nutrients of a particular Ingredient")
    @RequestMapping("/ingredients/{ingredientId}/nutrients")
    public List<Nutrients> getAllNutrientsByIngredients(@PathVariable BigInteger ingredientId){
         return nutrientService.getAllNutrientsByIngredients(ingredientId);
    }

    @ApiOperation(value="Return a particular nutrient of a particular Ingredient")
    @RequestMapping("/ingredients/{ingredientId}/nutrients/{nutrientId}")
    public Nutrients getNutrientByIngredient(@PathVariable BigInteger ingredientId,@PathVariable BigInteger nutrientId){
        return nutrientService.getNutrientByIngredient(ingredientId,nutrientId);
    }

    @ApiOperation(value="Returns all Nutrients of a Product")
    @RequestMapping("products/{productId}/ingredients/{ingredientId}/nutrients")
    public List<Nutrients> getAllNutrientByProduct(@PathVariable BigInteger ingredientId,@PathVariable BigInteger productId){
        return nutrientService.getAllNutrientsOfProduct(ingredientId,productId);
    }

    @ApiOperation(value="Return all nutrients associated with an order made by a user for a particular product,associated with its ingredients")
    @RequestMapping("users/{userId}/orders/{orderId}/products/{productId}/ingredients/{id}/nutrients")
    public List<Nutrients> getAllNutrients(@PathVariable BigInteger id) {
        return nutrientService.getAllNutrients(id);
    }

    @ApiOperation(value="Return a particular nutrient associated with an order made by a user for a particular product,associated with its ingredients")
    @RequestMapping("users/{userId}/orders/{orderId}/products/{productId}/ingredients/{ingredientId}/nutrients/{id}")
    public Nutrients getNutrient(@PathVariable BigInteger id,@PathVariable BigInteger ingredientId){

        return nutrientService.getNutrient(ingredientId,id);
    }

    @ApiOperation(value="Adds a new nutrient to a product")
    @RequestMapping(method = RequestMethod.POST,value = "products/{productId}/ingredients/{ingredientId}/nutrient")
    public Nutrients  addNutrient(@RequestBody Nutrients nutrients,@PathVariable BigInteger ingredientId,@PathVariable BigInteger productId){
        nutrients.setIngredients(new Ingredients(ingredientId, ""));
        nutrients.setProduct(new Product(productId,0.0));
        return nutrientService.addNutrient(nutrients);
    }

    @ApiOperation(value="Adds a List of new Nutrients to a product")
    @RequestMapping(method = RequestMethod.POST,value = "products/{productId}/ingredients/{ingredientId}/nutrients")
    public List<Nutrients>  addNutrient(@RequestBody List<Nutrients> nutrients,@PathVariable BigInteger ingredientId,@PathVariable BigInteger productId) {
        for (int i = 0; i < nutrients.size(); i++){
            nutrients.get(i).setIngredients(new Ingredients(ingredientId, ""));
            nutrients.get(i).setProduct(new Product(productId,0.0));}
        return nutrientService.addNutrient(nutrients);
    }

    @ApiOperation(value="Adds a new nutrient ")
    @RequestMapping(method = RequestMethod.POST,value = "users/{userId}/orders/{orderId}/products/{productId}/ingredients/{ingredientId}/nutrient")
    public Nutrients  addNutrient(@RequestBody Nutrients nutrients,@PathVariable BigInteger ingredientId,@PathVariable BigInteger productId,@PathVariable BigInteger userId,@PathVariable BigInteger orderId){
        nutrients.setIngredients(new Ingredients(ingredientId,"",productId,orderId,userId));
        nutrients.setProduct(new Product(productId,0.0,orderId,userId));
        return nutrientService.addNutrient(nutrients);
    }

    @ApiOperation(value="Adds a List of new Nutrients")
    @RequestMapping(method = RequestMethod.POST,value = "users/{userId}/orders/{orderId}/products/{productId}/ingredients/{ingredientId}/nutrients")
    public List<Nutrients>  addNutrient(@RequestBody List<Nutrients> nutrients,@PathVariable BigInteger ingredientId,@PathVariable BigInteger productId,@PathVariable BigInteger userId,@PathVariable BigInteger orderId) {
        for (int i = 0; i < nutrients.size(); i++){
            nutrients.get(i).setIngredients(new Ingredients(ingredientId, "", productId, orderId, userId));
            nutrients.get(i).setProduct(new Product(productId,0.0,orderId,userId));}
            return nutrientService.addNutrient(nutrients);
        }


    @ApiOperation(value="Alter a nutrient")
    @RequestMapping(method = RequestMethod.PUT, value="/products/{productId}/ingredients/{ingredientId}/nutrients/{id}")
    public Nutrients updateNutrient(@RequestBody Nutrients nutrients,@PathVariable BigInteger id,@PathVariable BigInteger ingredientId,@PathVariable BigInteger productId)throws  Exception {
        nutrients.setIngredients(new Ingredients(ingredientId," ",productId));
        return nutrientService.updateNutrient(nutrients,id,ingredientId);
    }

    @ApiOperation(value="Delete a Nutrient")
    @RequestMapping(method = RequestMethod.DELETE, value="users/{userId}/orders/{orderId}/products/{productId}/ingredients/{ingredientId}/nutrients/{id}")
    public void deleteUser(@PathVariable BigInteger id){
        nutrientService.deleteNutrient(id);
    }
}
