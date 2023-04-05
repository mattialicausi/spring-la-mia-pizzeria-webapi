package org.spring.pizzeria.crud.repository;

import org.spring.pizzeria.crud.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Integer> {


}
