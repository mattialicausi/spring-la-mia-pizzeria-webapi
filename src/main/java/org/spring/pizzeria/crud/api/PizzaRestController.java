package org.spring.pizzeria.crud.api;

import org.spring.pizzeria.crud.model.Pizza;
import org.spring.pizzeria.crud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/pizza")
public class PizzaRestController {

    @Autowired
    private PizzaService pizzaService;

    //tutte le pizze
    @GetMapping
    public List<Pizza> list (@RequestParam(name = "q") Optional<String> search) {

        if(search.isPresent()) {
            return pizzaService.getFilteredPizze(search.get());
        }

        return pizzaService.getAllPizze();

    }
}
