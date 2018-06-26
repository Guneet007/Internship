package com.example.API.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NutrientException extends RuntimeException{
    public NutrientException(String exception){super(exception);}
}
