package org.spring.pizzeria.crud.repository;

import org.spring.pizzeria.crud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {


}
