package org.spring.pizzeria.crud.exceptions;

public class OfferNotFoundException extends RuntimeException{
    public OfferNotFoundException(String message) {
        super(message);
    }
}
