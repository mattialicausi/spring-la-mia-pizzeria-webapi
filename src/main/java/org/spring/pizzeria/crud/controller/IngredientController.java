package org.spring.pizzeria.crud.controller;

import jakarta.validation.Valid;
import org.spring.pizzeria.crud.exceptions.IngredientNotFoundException;
import org.spring.pizzeria.crud.model.AlertMessage;
import org.spring.pizzeria.crud.model.Ingredient;
import org.spring.pizzeria.crud.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    IngredientService ingredientService;

    @GetMapping()
    public String index(Model model) {

        List<Ingredient> ingredients;

        try {
            ingredients = ingredientService.getAll();
            model.addAttribute("ingredients", ingredients);
            model.addAttribute("newIngredient", new Ingredient());
        } catch (IngredientNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient not found");
        }

        return "/ingredient/index";

    }

    @PostMapping("/save")
    public String doSave(@Valid @ModelAttribute(name="newIngredient") Ingredient ingredient, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("list", ingredientService.getAll());
            return "/ingredient/index";
        }

        ingredientService.create(ingredient);
        return "redirect:/ingredient";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        try {
            boolean success = ingredientService.deleteById(id);

            if (success) {

                redirectAttributes.addFlashAttribute("message",
                        new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Ingrediente con id " + id + " eliminato"));

            } else {

                redirectAttributes.addFlashAttribute("message",
                        new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Ingrediente con id " + id + " non può essere eliminato"));
            }
        } catch (IngredientNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",
                    new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Ingrediente con id " + id + " non trovato"));
        }

        return "redirect:/ingredient";

    }
}
