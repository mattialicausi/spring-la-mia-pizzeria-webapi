package org.spring.pizzeria.crud.controller;

import org.spring.pizzeria.crud.model.Pizza;
import org.spring.pizzeria.crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

    //SPRING INIETTA LA DIPENDENZA COME FIELD INJECTION
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(Model model) {

        List<Pizza> pizze = pizzaRepository.findAll();

        model.addAttribute("pizzeList", pizze);

        return "/pizza/index";
    }
}
