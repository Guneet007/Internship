package com.example.API.Exceptions;


import com.example.API.Entities.Ingredients;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IngredientException extends RuntimeException{
    public IngredientException(String exception){super(exception);}
}
