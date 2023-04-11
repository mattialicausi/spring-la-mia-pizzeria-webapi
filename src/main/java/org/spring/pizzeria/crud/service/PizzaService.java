package org.spring.pizzeria.crud.service;

import org.spring.pizzeria.crud.exceptions.PizzaNotFoundException;
import org.spring.pizzeria.crud.model.Ingredient;
import org.spring.pizzeria.crud.model.Pizza;
import org.spring.pizzeria.crud.repository.IngredientRepository;
import org.spring.pizzeria.crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PizzaService {

    @Autowired
    PizzaRepository pizzaRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    public Pizza createPizza(Pizza formPizza) {

        Pizza pizzaToPersist = new Pizza();

        pizzaToPersist.setName(formPizza.getName());
        pizzaToPersist.setDescription(formPizza.getDescription());
        pizzaToPersist.setPrice(new BigDecimal(String.valueOf(formPizza.getPrice())));
        pizzaToPersist.setImage(formPizza.getImage());
        pizzaToPersist.setIngredients(formPizza.getIngredients());

        Set<Ingredient> formIngredient = getPizzaIngredients(formPizza);
        pizzaToPersist.setIngredients(formIngredient);


        return pizzaRepository.save(pizzaToPersist);

    }

    public Pizza getById(Integer id) {

        Optional<Pizza> result = pizzaRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PizzaNotFoundException(Integer.toString(id));
        }

    }

    public List<Pizza> getFilteredPizze(String keyword) {
        return pizzaRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Pizza> getAllPizze() {
        return pizzaRepository.findAll(Sort.by("name"));
    }


    // metodo che torna vero o falso se trova l'id di una pizza nel DB
    public boolean deleteById(Integer id) {

        pizzaRepository.findById(id).orElseThrow(()-> new PizzaNotFoundException("Pizza con id " + id + " non trovata"));

        try {
            pizzaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Pizza updatePizza(Pizza formPizza, Integer id) {

        Pizza pizzaToPersist = getById(id);

        pizzaToPersist.setName(formPizza.getName());
        pizzaToPersist.setDescription(formPizza.getDescription());
        pizzaToPersist.setPrice(new BigDecimal(String.valueOf(formPizza.getPrice())));
        pizzaToPersist.setImage(formPizza.getImage());
        pizzaToPersist.setIngredients(formPizza.getIngredients());

        Set<Ingredient> formIngredient = getPizzaIngredients(formPizza);
        pizzaToPersist.setIngredients(formIngredient);


        return pizzaRepository.save(pizzaToPersist);

    }

    private Set<Ingredient> getPizzaIngredients(Pizza formPizza) {
        Set<Ingredient> formIngredients = new HashSet<>();

        for(Ingredient i : formPizza.getIngredients()) {
            formIngredients.add(ingredientRepository.findById(i.getId()).orElseThrow());
        }

        return formIngredients;

    }
}
