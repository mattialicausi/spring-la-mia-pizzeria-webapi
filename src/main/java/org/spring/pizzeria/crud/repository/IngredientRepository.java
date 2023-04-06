package org.spring.pizzeria.crud.repository;

import org.spring.pizzeria.crud.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {


}
