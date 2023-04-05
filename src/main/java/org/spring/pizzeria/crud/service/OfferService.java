package org.spring.pizzeria.crud.service;

import org.spring.pizzeria.crud.model.Offer;
import org.spring.pizzeria.crud.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    public Offer create(Offer formOffer) {
        return offerRepository.save(formOffer);
    }

}
