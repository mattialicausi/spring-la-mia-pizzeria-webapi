package org.spring.pizzeria.crud.exceptions;

public class PizzaNotFoundException extends RuntimeException{

    public PizzaNotFoundException(String message) {
        super(message);
    }

}
