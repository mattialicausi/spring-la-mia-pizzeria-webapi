package org.spring.pizzeria.crud.controller;

import org.spring.pizzeria.crud.model.Pizza;
import org.spring.pizzeria.crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

    //SPRING INIETTA LA DIPENDENZA COME FIELD INJECTION
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(Model model, @RequestParam(name = "q") Optional<String> keyword, @RequestParam(name = "price") Optional<Integer> price) {

        List<Pizza> pizze = Collections.emptyList();

        if (keyword.isEmpty() && price.isEmpty()){

            pizze = pizzaRepository.findAll(Sort.by("name"));

        } else if (keyword.isPresent() && price.isPresent()) {

            pizze = pizzaRepository.findByNameContainingIgnoreCaseAndPriceLessThan(keyword.get(), price.get());
            model.addAttribute("keyword", keyword.get());
            model.addAttribute("price", price.get());


        }else if(price.isEmpty()) {

            pizze = pizzaRepository.findByNameContainingIgnoreCase(keyword.get());
            model.addAttribute("keyword", keyword.get());

        } else if (keyword.isEmpty()) {

            pizze = pizzaRepository.findByPriceLessThan(price.get());
            model.addAttribute("price", price.get());

        }

        model.addAttribute("pizzeList", pizze);
        return "/pizza/index";

    }

    @GetMapping("/{pizzaId}")
    public String show(@PathVariable("pizzaId") Integer id, Model model) {

        Optional<Pizza> result = pizzaRepository.findById(id);

        if(result.isPresent()) {

            model.addAttribute("pizza", result.get());

            return "/pizza/show";

        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");

        }

    }
}
