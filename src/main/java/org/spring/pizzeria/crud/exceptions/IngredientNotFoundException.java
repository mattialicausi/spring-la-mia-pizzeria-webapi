package org.spring.pizzeria.crud.exceptions;

public class IngredientNotFoundException extends RuntimeException{
    public IngredientNotFoundException(String message) {
        super(message);
    }
}
