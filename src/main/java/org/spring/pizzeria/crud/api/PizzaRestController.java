package org.spring.pizzeria.crud.api;

import jakarta.validation.Valid;
import org.spring.pizzeria.crud.exceptions.PizzaNotFoundException;
import org.spring.pizzeria.crud.model.Pizza;
import org.spring.pizzeria.crud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    //pizza singola
    @GetMapping("/{id}")
    public Pizza getById(@PathVariable Integer id) {

        try {
            return pizzaService.getById(id);
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    //crea pizza
    @PostMapping
    public Pizza create(@Valid @RequestBody Pizza pizza) {

        try {
            return pizzaService.createPizza(pizza);
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    // modifica pizza
    @PutMapping("/{id}")
    public Pizza update(@PathVariable Integer id, @Valid @RequestBody Pizza pizza) {

        try {
            return pizzaService.updatePizza(pizza, id);
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    //delete pizza
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        try {
            boolean success = pizzaService.deleteById(id);

            if(!success) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Impossibile eliminare perchè ha degli ingredienti");
            }
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

}
