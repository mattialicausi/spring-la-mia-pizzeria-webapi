package org.spring.pizzeria.crud.service;

import org.spring.pizzeria.crud.exceptions.IngredientNotFoundException;
import org.spring.pizzeria.crud.model.Ingredient;
import org.spring.pizzeria.crud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredient> getAll() {
        return ingredientRepository.findAll(Sort.by("name"));
    }


    public Ingredient create(Ingredient formIngredient) {
        Ingredient ingredientToCreate = new Ingredient();
        ingredientToCreate.setName(formIngredient.getName());
        return ingredientRepository.save(ingredientToCreate);
    }

    public boolean deleteById(Integer id) {

        ingredientRepository.findById(id).orElseThrow(()-> new IngredientNotFoundException("Ingredient con id " + id + " non trovato"));

        try {
            ingredientRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
