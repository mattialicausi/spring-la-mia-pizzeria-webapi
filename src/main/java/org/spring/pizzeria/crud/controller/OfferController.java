package org.spring.pizzeria.crud.controller;

import jakarta.validation.Valid;
import org.spring.pizzeria.crud.exceptions.PizzaNotFoundException;
import org.spring.pizzeria.crud.model.Offer;
import org.spring.pizzeria.crud.model.Pizza;
import org.spring.pizzeria.crud.service.OfferService;
import org.spring.pizzeria.crud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/offer")
public class OfferController {

    @Autowired
    PizzaService pizzaService;

    @Autowired
    private OfferService offerService;

    @GetMapping("/create")
    public String create(@RequestParam(name = "pizzaId") Optional<Integer> id , Model model) {

        Offer offer = new Offer();
        offer.setStartDate(LocalDate.now());
        offer.setEndDate(LocalDate.now().plusMonths(1));

        if (id.isPresent()) {

            try {
                Pizza pizza = pizzaService.getById(id.get());
                offer.setPizza(pizza);
            } catch (PizzaNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

        }

        model.addAttribute("offers", offer);

        return "/offer/create";

    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute Offer formOffer, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/offer/create";
        }

        Offer newOffer = offerService.create(formOffer);
        return "redirect:/pizza/" + Integer.toString(newOffer.getPizza().getId());

    }
}
