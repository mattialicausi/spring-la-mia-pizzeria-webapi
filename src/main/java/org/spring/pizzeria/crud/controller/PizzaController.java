package org.spring.pizzeria.crud.controller;

import jakarta.validation.Valid;
import org.spring.pizzeria.crud.exceptions.PizzaNotFoundException;
import org.spring.pizzeria.crud.model.AlertMessage;
import org.spring.pizzeria.crud.model.AlertMessage.AlertMessageType;
import org.spring.pizzeria.crud.model.Pizza;
import org.spring.pizzeria.crud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

    //SPRING INIETTA LA DIPENDENZA COME FIELD INJECTION
//    @Autowired
//    private PizzaRepository pizzaRepository;

    @Autowired
    PizzaService pizzaService;

    //INDEX
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

    //SHOW

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

    //CREATE

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

    //UPDATE

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {

        try {

            Pizza pizza = pizzaService.getById(id);
            model.addAttribute("pizza", pizza);
            return "/pizza/edit";

        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con id " + id + " non trovata");
        }


    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            return "/pizza/edit";
        }

        try {

            Pizza updatedPizza = pizzaService.updatePizza(formPizza, id);
            return "redirect:/pizza/" + Integer.toString(updatedPizza.getId());

        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con id " + id + "non trovata");
        }

    }

    //DELETE

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        try {

            boolean success = pizzaService.deleteById(id);

            if (success) {

                redirectAttributes.addFlashAttribute("message",
                        new AlertMessage(AlertMessageType.SUCCESS, "Pizza con id " + id + " eliminata"));

            } else {

                redirectAttributes.addFlashAttribute("message",
                        new AlertMessage(AlertMessageType.SUCCESS, "Pizza con id " + id + " non può essere eliminata"));
            }



        } catch (PizzaNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",
                    new AlertMessage(AlertMessageType.SUCCESS, "Pizza con id " + id + " non trovata"));
        }

        return "redirect:/pizza";

    }
}
