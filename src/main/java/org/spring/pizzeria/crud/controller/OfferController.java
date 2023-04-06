package org.spring.pizzeria.crud.controller;

import jakarta.validation.Valid;
import org.spring.pizzeria.crud.exceptions.OfferNotFoundException;
import org.spring.pizzeria.crud.exceptions.PizzaNotFoundException;
import org.spring.pizzeria.crud.model.AlertMessage;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/offer")
public class OfferController {

    @Autowired
    PizzaService pizzaService;

    @Autowired
    OfferService offerService;

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

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        try {
            Offer offer = offerService.getById(id);
            model.addAttribute("offer", offer);
            return "/offer/edit";
        } catch (OfferNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza with id " + id + " not found");
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute Offer formOffer, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
//            return "redirect:/offer/edit/{id}";
            return "/offer/edit";
        }

        try {

            Offer updatedOffer = offerService.updateOffer(formOffer, id);
            return "redirect:/pizza/" + (updatedOffer.getPizza().getId());

        } catch (OfferNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer con id " + id + "non trovata");
        }

    }

    //delete

    @GetMapping("/delete/{id}")
    public String delete (@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        try {
            boolean success = offerService.deleteById(id);

            if(success) {
                redirectAttributes.addFlashAttribute("message",
                        new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Offerta con id " + id + " eliminata con successo."));

            } else {
                redirectAttributes.addFlashAttribute("message",
                        new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Offerta con id " + id + " non trovata."));
            }

        } catch (OfferNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",
                    new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Offerta con id " + id + " non trovata."));

        }

        return "redirect:/pizza/{id}";

    }
}
