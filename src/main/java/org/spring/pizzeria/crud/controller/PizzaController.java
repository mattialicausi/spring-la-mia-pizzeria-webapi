package org.spring.pizzeria.crud.controller;

import jakarta.validation.Valid;
import org.spring.pizzeria.crud.exceptions.PizzaNotFoundException;
import org.spring.pizzeria.crud.model.Pizza;
import org.spring.pizzeria.crud.repository.PizzaRepository;
import org.spring.pizzeria.crud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

    //SPRING INIETTA LA DIPENDENZA COME FIELD INJECTION
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    PizzaService pizzaService;

    @GetMapping
    public String index(Model model, @RequestParam(name = "q") Optional<String> keyword) {

        List<Pizza> pizze;

        if (keyword.isEmpty()){

            pizze = pizzaService.getAllPizze();

        } else {

            pizze = pizzaService.getFilteredPizze(keyword.get());
            model.addAttribute("keyword", keyword.get());

        }

        model.addAttribute("pizzeList", pizze);
        return "/pizza/index";

    }

    @GetMapping("/{pizzaId}")
    public String show(@PathVariable("pizzaId") Integer id, Model model) {

        try {
            Pizza pizza = pizzaService.getById(id);
            model.addAttribute("pizza", pizza);
            return "/pizza/show";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }

    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "/pizza/create";
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "/pizza/create";
        }

        pizzaService.createPizza(formPizza);

        return "redirect:/pizza";


    }
}
