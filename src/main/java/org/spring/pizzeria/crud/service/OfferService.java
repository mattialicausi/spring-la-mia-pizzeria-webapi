package org.spring.pizzeria.crud.service;

import org.spring.pizzeria.crud.exceptions.PizzaNotFoundException;
import org.spring.pizzeria.crud.model.Offer;
import org.spring.pizzeria.crud.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OfferService {

    @Autowired
    OfferRepository offerRepository;

    public Offer create(Offer formOffer) {
        return offerRepository.save(formOffer);
    }

    public Offer getById(Integer id) throws PizzaNotFoundException{

        Optional<Offer> result = offerRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PizzaNotFoundException(Integer.toString(id));
        }

    }

    public Offer updateOffer(Offer formOffer, Integer id) throws PizzaNotFoundException {

        Offer offerToSave = getById(id);

        offerToSave.setTitle(formOffer.getTitle());
        offerToSave.setStartDate(formOffer.getStartDate());
        offerToSave.setEndDate(formOffer.getEndDate());
//        offerToPersist.setPizza(formOffer.getPizza());



        return offerRepository.save(offerToSave);

    }

}
