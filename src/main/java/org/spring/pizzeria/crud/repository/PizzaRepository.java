package org.spring.pizzeria.crud.repository;

import org.spring.pizzeria.crud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    public List<Pizza> findByNameContainingIgnoreCase(String name);

    public List<Pizza> findByPriceLessThan(Integer price);

    List<Pizza> findByNameContainingIgnoreCaseAndPriceLessThan(String name, Integer price);

}
